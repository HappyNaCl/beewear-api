package com.beewear.api.infrastructure.adapter.persistence.seeder;

import com.beewear.api.application.ports.outbound.documents.ProductDocumentPort;
import com.beewear.api.application.ports.outbound.security.PasswordHasherPort;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.entities.enums.ProductStatus;
import com.beewear.api.infrastructure.adapter.elasticsearch.repository.ElasticsearchProductRepository;
import com.beewear.api.infrastructure.adapter.persistence.mappers.ProductJpaMapper;
import com.beewear.api.infrastructure.adapter.persistence.models.ProductImageJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.models.UserJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringProductRepository;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("seeder")
public class DummyDataSeeder implements CommandLineRunner {
    private final SpringUserRepository userRepository;
    private final SpringProductRepository productRepository;
    private final ElasticsearchProductRepository productElasticsearchRepository;
    private final ProductDocumentPort productDocumentPort;
    private final ProductJpaMapper productJpaMapper;
    private final PasswordHasherPort hasher;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private void seedUser() {
        int userCount = 50;


        for(int i = 0; i < userCount; i++) {
            UserJpaModel user = new UserJpaModel();
            user.setUsername(faker.credentials().username());
            user.setPassword(hasher.hashPassword("password"));
            user.setEmail(faker.internet().emailAddress());
            user.setGender(random.nextInt(2) == 1 ? Gender.FEMALE : Gender.MALE);
            userRepository.save(user);
        }
    }

    private void seedProduct() {
        if (productRepository.count() > 0) {
            System.out.println("Products already exist, skipping seeding.");
            return;
        }

        List<UserJpaModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found. Create at least one user before seeding products.");
            return;
        }

        productElasticsearchRepository.deleteAll();

        int productCount = 750;

        for(int i = 0; i < productCount; i++) {
            ProductJpaModel product = new ProductJpaModel();

            product.setName(faker.commerce().productName());
            product.setDescription(faker.lorem().sentence(10, 5));
            product.setPrice(Double.parseDouble(faker.commerce().price(10000.0, 500000.0)));

            product.setProductCategory(randomEnum(ProductCategory.class));
            product.setForGender(randomEnum(Gender.class));
            product.setStatus(ProductStatus.ACTIVE);

            UserJpaModel creator = users.get(random.nextInt(users.size()));
            product.setCreator(creator);

            ProductImageJpaModel image = new ProductImageJpaModel();
            image.setProduct(product);
            image.setPublicId(UUID.randomUUID().toString());
            image.setImageUrl("https://picsum.photos/seed/" + UUID.randomUUID() + "/600/800");

            product.getProductImages().add(image);

            ProductJpaModel savedModel = productRepository.save(product);
            Product domainModel = productJpaMapper.toDomain(savedModel);
            productDocumentPort.addProduct(domainModel);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        seedUser();
        seedProduct();
    }

    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        T[] values = clazz.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
