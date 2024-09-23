# Embracing Clean Architecture in Spring Boot and Kotlin

This repository is used to demonstrate the evolutionary process of system architecture as it adapts to growing
business requirements. Each step showcases a progressive enhancement of the shopping platform.

## Steps Overview

The project is structured in a way to show how architectural decisions are made over time, based on new business
requirements and technical considerations. The steps below will guide you through the transformation from a simple
system to a more flexible, maintainable architecture:

1. **Step 1**: Product Listings
2. **Step 2**: Discounts
3. **Step 3**: Discounts Campaign

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

---

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

---

## Step 3: Discount Campaigns

### Business Requirement 3: Plan and Schedule Discount Campaigns

- **Description**: The system should support the creation and scheduling of discount campaigns that apply specific discounts to products for a defined period. Each campaign can apply multiple discount rules and should be active only during its scheduled time frame.

### Functional Requirements

1. **Create Discount Campaign**:
    - **Description**: The user can create a new discount campaign by specifying the start and end dates, the discount type, and the applicable products.
    - **Acceptance Criteria**:
        - The user provides the campaign name, start date, end date, discount type(s), and target products.
        - The system schedules the campaign based on the provided dates.
        - The discount rules are applied only during the active campaign period.
        - The system should prevent overlapping discount campaigns for the same products unless specified.

2. **View Active Campaigns**:
    - **Description**: The user can retrieve a list of all active discount campaigns at any given time.
    - **Acceptance Criteria**:
        - The system returns a list of currently active campaigns with their details, such as start date, end date, discount types, and affected products.

3. **Deactivate Expired Campaigns**:
    - **Description**: The system should automatically deactivate campaigns once their end date has passed.
    - **Acceptance Criteria**:
        - The system automatically checks the campaign end date and deactivates the campaign after it expires.
        - Expired campaigns should no longer apply discounts to products.
