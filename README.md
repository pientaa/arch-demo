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

## Step 2: Apply Discounts to Products Based on Specific Rules

### Business Requirement 2: Apply Discounts to Products Based on Specific Rules

- **Description**: The system should support different types of discounts that can be applied to products based on
  specific business rules. Each discount type will have its own method of calculation and criteria for application.

- **Types of Discounts**:
    - **Buy N for the Price of 1**: Allows customers to buy multiple units of the same product for the price of one.
    - **Count-Based Percentage Discount**: A percentage discount is applied when a customer buys a certain number of a
      specific product (e.g., 10% off for buying 3 or more of a product).

### Functional Requirements

1. **Apply "Buy N for the Price of 1" Discount**:
    - **Description**: When a product qualifies for the "buy N for the price of 1" discount, a customer can purchase
      multiple units of the product for the price of a single unit.
    - **Acceptance Criteria**:
        - The system identifies products eligible for this discount.
        - The final price is calculated based on the number of items purchased and the applicable discount.
        - The system stores the original price of products and discounts.

2. **Apply Count-Based Percentage Discount**:
    - **Description**: When a customer purchases a certain quantity of a product, a percentage discount is applied to
      the price.
    - **Acceptance Criteria**:
        - The system checks the quantity of the product and applies a percentage discount if the purchase
          meets the threshold.
        - The percentage discount is stored along with the original price.
