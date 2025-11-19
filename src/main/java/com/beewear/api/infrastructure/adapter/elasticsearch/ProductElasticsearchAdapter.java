package com.beewear.api.infrastructure.adapter.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.beewear.api.application.ports.outbound.documents.ProductDocumentPort;
import com.beewear.api.application.services.dto.ProductDto;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.ProductImage;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.infrastructure.adapter.elasticsearch.models.ProductDocument;
import com.beewear.api.infrastructure.adapter.elasticsearch.repository.ElasticsearchProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class ProductElasticsearchAdapter implements ProductDocumentPort {

    private ElasticsearchProductRepository repo;
    private ElasticsearchOperations operations;


    @Override
    public List<Product> searchProducts(String query,
                                           Double minPrice,
                                           Double maxPrice,
                                           Gender gender,
                                           ProductCategory category,
                                           Pageable pageable) {
        NativeQuery nativeQuery = buildSearchQuery(query, minPrice, maxPrice, gender, category, pageable);
        var searchHits = operations.search(nativeQuery, ProductDocument.class);

        return searchHits.map(hit -> {
            ProductDocument doc = hit.getContent();
            Product product = new Product();
            product.setId(doc.getId());
            product.setName(doc.getName());
            product.setDescription(doc.getDescription());
            product.setPrice(doc.getPrice());
            product.setForGender(doc.getForGender());
            product.setProductCategory(doc.getProductCategory());

            List<ProductImage> productImages = new ArrayList<>();
            ProductImage image = new ProductImage();
            image.setImageUrl(doc.getFirstImageUrl());
            productImages.add(image);

            product.setProductImages(productImages);
            product.setCreatorId(doc.getCreatorId());
            product.setCreatedAt(doc.getCreatedAt());
            return product;
        }).toList();
    }

    @Override
    public void addProduct(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId());
        doc.setName(product.getName());
        doc.setDescription(product.getDescription());
        doc.setPrice(product.getPrice());
        doc.setFirstImageUrl(product.getProductImages().get(0).getImageUrl());
        doc.setProductCategory(product.getProductCategory());
        doc.setForGender(product.getForGender());
        doc.setCreatorId(product.getCreatorId());
        doc.setCreatedAt(product.getCreatedAt());

        repo.save(doc);
    }

    @Override
    public void removeProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    private NativeQuery buildSearchQuery(String query,
                                         Double minPrice,
                                         Double maxPrice,
                                         Gender gender,
                                         ProductCategory category,
                                         Pageable pageable) {

        BoolQuery.Builder bool = new BoolQuery.Builder();

        if (query != null && !query.isEmpty()) {
            bool.must(must -> must
                    .multiMatch(m -> m
                            .query(query)
                            .fields(Arrays.asList("name", "description"))
                            .fuzziness("AUTO")
                            .prefixLength(1)
                            .type(TextQueryType.BestFields)
                    )
            );
        }


        if(gender != null) {
            bool.filter(f -> f.term(t -> t.field("forGender").value(gender.toString())));
        }

        if(category != null) {
            bool.filter(f -> f.term(t -> t.field("productCategory").value(category.toString())));
        }

        if(minPrice != null) {
            bool.filter(f -> f.range(r ->
                    r.number(n -> n.field("price").gte(minPrice))
            ));
        }

        if(maxPrice != null) {
            bool.filter(f -> f.range(r ->
                    r.number(n -> n.field("price").lte(maxPrice))
            ));
        }

        return NativeQuery.builder()
                .withQuery(q -> q.bool(bool.build()))
                .withPageable(pageable)
                .build();
    }
}
