workspace {

    model {
        user = person "User" "A user of the shopping platform"

        productSystem = softwareSystem "Product System" "Manages product listings, discounts, and price calculations in a hexagonal architecture" {

            productWebapp = container "ProductController" "Handles HTTP requests for products and discounts" "Spring Boot REST Controller"

            productService = container "ProductService" "Business logic for managing products" "Spring Service"
            discountService = container "DiscountService" "Business logic for managing discounts" "Spring Service"
            calculatePriceUseCase = container "CalculatePriceUseCase" "Executes price calculations" "Spring Service"

            productRepositoryPort = container "ProductRepository (Port)" "Interface for product persistence" "Interface"

            productRepositoryAdapter = container "ProductRepositoryImpl (Adapter)" "Implements product persistence using JPA" "Spring Component"

            jpaProductRepository = container "JpaProductRepository" "Spring JPA repository for ProductEntity" "Spring Data JPA"

            pricingService = container "PricingService" "Calculates pricing for products" "Spring Service"

            user -> productWebapp "Uses"

            productWebapp -> productService "Delegates product operations"
            productWebapp -> discountService "Delegates discount operations"
            productWebapp -> calculatePriceUseCase "Delegates price calculation requests"

            productService -> productRepositoryPort "Uses"
            discountService -> productRepositoryPort "Uses"
            calculatePriceUseCase -> productRepositoryPort "Fetches product data"
            calculatePriceUseCase -> pricingService "Delegates price calculation"

            productRepositoryAdapter -> productRepositoryPort "Implements"
            productRepositoryAdapter -> jpaProductRepository "Uses for data persistence"
        }
    }

    views {
        systemContext productSystem {
            include *
            autolayout lr
        }

        container productSystem {
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
