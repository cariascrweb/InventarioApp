# amarillo.storecr Inventory Management Application

## Summary
This web application allows users to manage inventory, sales, and expenses for the virtual store **amarillo.storecr**. It features:

1. **Authentication:**
   - Google login using OAuth2.
   - Email/password-based login with encrypted credentials stored in MariaDB.

2. **Inventory Tracking:**
   - Keep track of inventory purchases, sales, and expenses.
   - Set and dynamically update stock thresholds for alerts.

3. **Dynamic Dashboard:**
   - Displays real-time inventory, sales, revenue, and expense data.

4. **Email Alerts:**
   - Automatically notify users when stock levels fall below a user-defined percentage.

## Roadmap

### Phase 1: Initial Setup
- [] Set up Spring Boot backend.
- [] Configure MariaDB for data storage.
- [] Implement user registration and login with email/password.
- [] Configure Google OAuth2 login.
- [] Build a basic frontend interface for login and dashboard.

### Phase 2: Core Functionality
- [ ] Develop CRUD functionality for inventory management.
  - Add purchases.
  - Track sales.
  - Record expenses.
- [ ] Create backend endpoints for dashboard data aggregation.
- [ ] Implement email alerts for stock levels.

### Phase 3: Dashboard and Analytics
- [ ] Design a responsive and interactive frontend dashboard.
  - Display inventory statistics.
  - Visualize sales and revenue trends with charts.
- [ ] Add user-configurable stock threshold settings.

### Phase 4: Security and Testing
- [ ] Secure API endpoints with role-based authentication.
- [ ] Perform unit and integration testing for backend and frontend.
- [ ] Encrypt sensitive data (e.g., user passwords and email configurations).

### Phase 5: Deployment and Scaling
- [ ] Deploy the application to a cloud platform (e.g., AWS, GCP, Heroku).
- [ ] Set up CI/CD pipelines for automated testing and deployment.
- [ ] Optimize database queries and backend performance.

### Phase 6: Enhancements
- [ ] Add multi-language support for broader accessibility.
- [ ] Implement multi-store management for scalability.
- [ ] Integrate advanced analytics with AI-based sales forecasting.
