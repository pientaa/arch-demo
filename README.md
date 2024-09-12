# Embracing Clean Architecture in Spring Boot and Kotlin

This repository is used to demonstrate the evolutionary process of system architecture as it adapts to growing
business requirements. Each step showcases a progressive enhancement of the shopping platform.

## Steps Overview

The project is structured in a way to show how architectural decisions are made over time, based on new business
requirements and technical considerations. The steps below will guide you through the transformation from a simple
system to a more flexible, maintainable architecture:

1. **Step 1**: Product Listings

---

## Step 1: Product Listings

### Business Requirement 1: Manage Product Listings

- **Description**: The system should allow the user to manage product listings, including adding new products and
  retrieving a list of all products.

### Functional Requirements

1. **Add New Product**:
    - **Description**: The user can add a new product with a name and price to the inventory.
    - **Acceptance Criteria**:
        - The user provides the name and price of the product.
        - The product is stored in the database.
        - The product should have a unique identifier.

2. **Retrieve List of Products**:
    - **Description**: The user can retrieve a list of all available products in the system.
    - **Acceptance Criteria**:
        - The user makes a request to retrieve all products.
        - The system returns a list of all products with their name, price, and ID.
