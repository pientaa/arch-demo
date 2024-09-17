workspace {

    model {
        user = person "User" "A user of the shopping platform"

        ims = softwareSystem "Product Listings" "Manages product listings and discounts" {
            productWebapp = container "ProductController" "Handles HTTP requests for products" "Spring MVC"

            productService = container "ProductService" "Business logic for products" "Spring Service"
            discountService = container "DiscountService" "Business logic for discounts" "Spring Service"

            productRepository = container "ProductRepository" "Handles product data persistence" "JPA Repository"

            user -> productWebapp "Uses"

            productWebapp -> productService "Handles product requests"
            productWebapp -> discountService "Handles discount requests"

            productService -> productRepository "Uses"

            discountService -> productRepository "Uses"
        }
    }

    views {
        systemContext ims {
            include *
            autolayout lr
        }

        container ims {
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