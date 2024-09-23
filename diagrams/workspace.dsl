workspace {

    model {
        user = person "User" "A user of the shopping platform"
        admin = person "Admin" "An administrator managing discount campaigns"

        productListingsSystem = softwareSystem "Product Listings System" "Manages product listings and price calculations" {

            productWebapp = container "ProductController" "Handles HTTP requests for products" "Spring Boot REST Controller"

            productService = container "ProductService" "Business logic for managing products" "Spring Service"
            calculatePriceUseCase = container "CalculatePriceUseCase" "Executes price calculations" "Spring Service"

            productRepositoryPort = container "ProductRepository (Port)" "Interface for product persistence" "Interface"

            productRepositoryAdapter = container "ProductRepositoryImpl (Adapter)" "Implements product persistence using JPA" "Spring Component"

            jpaProductRepository = container "JpaProductRepository" "Spring JPA repository for ProductEntity" "Spring Data JPA"

            pricingService = container "PricingService" "Calculates pricing for products" "Spring Service"

            user -> productWebapp "Uses"

            productWebapp -> productService "Delegates product operations"
            productWebapp -> calculatePriceUseCase "Delegates price calculation requests"

            productService -> productRepositoryPort "Uses"
            calculatePriceUseCase -> productRepositoryPort "Fetches product data"
            calculatePriceUseCase -> pricingService "Delegates price calculation"

            productRepositoryPort -> productRepositoryAdapter "Implements"
            productRepositoryAdapter -> jpaProductRepository "Uses for data persistence"
        }

        discountCampaignsSystem = softwareSystem "Discount Campaigns System" "Manages discount campaigns and creates discounts for products" {

            discountWebapp = container "DiscountController" "Handles HTTP requests for discounts" "Spring Boot REST Controller"
            discountService = container "DiscountService" "Business logic for managing discounts" "Spring Service"
            discountRepositoryPort = container "DiscountRepository (Port)" "Interface for discount persistence" "Interface"
            discountRepositoryAdapter = container "DiscountRepositoryImpl (Adapter)" "Implements discount persistence using JPA" "Spring Component"
            jpaDiscountRepository = container "JpaDiscountRepository" "Spring JPA repository for DiscountEntity" "Spring Data JPA"

            admin -> discountWebapp "Manages discounts"

            discountWebapp -> discountService "Delegates discount operations"
            discountService -> discountRepositoryPort "Uses"
            discountRepositoryPort -> discountRepositoryAdapter "Implements"
            discountRepositoryAdapter -> jpaDiscountRepository "Uses for data persistence"

            // Discount Campaigns System creates discounts for products
            discountService -> productListingsSystem "Creates discounts for products"
        }

    }

    views {
        // System context view for Product Listings System, but including both users and systems
        systemContext productListingsSystem {
            include user
            include admin
            include productListingsSystem
            include discountCampaignsSystem
            autolayout lr
        }

        container productListingsSystem {
            include *
            autolayout lr
        }

        container discountCampaignsSystem {
            include *
            autolayout lr
        }

        theme default

        styles {
            element "Container" {
                background "#1168bd"
                color "#ffffff"
            }
            element "Person" {
                background "#08427b"
                color "#ffffff"
                shape person
            }
            element "Database" {
                shape cylinder
            }
        }
    }
}
