workspace {

    model {
        user = person "User" "A user of the shopping platform"

        ims = softwareSystem "Product Listings" "Manages product listings" {
            webapp = container "ProductController" "Handles HTTP requests" "Spring MVC"
            service = container "ProductService" "Business logic for products" "Spring Service"
            repository = container "ProductRepository" "Handles data persistence" "JPA Repository"

            database = container "Database" "Stores product data" "Relational Database"

            user -> webapp "Uses"
            webapp -> service "Handles"
            service -> repository "Calls"
            repository -> database "Reads/Writes"
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
