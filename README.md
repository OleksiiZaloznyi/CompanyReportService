# Infrastructure Development Agency Stock Market Service

## Project Description

This project aims to develop a RESTful service for managing company data and their reports for the Infrastructure Development Agency of the Stock Market in Ukraine.

## Project Setup Guide
1. **Clone the Repository.**
2. **Configure Databases.** You can change your DB, username, password in application.properties and docker-compose.yml.
3. **Build the Project.**
4. **Run Docker Compose.** Start Docker Compose to deploy the service and its associated databases.
5. After the service has been successfully started, you can utilize its API by **making requests** to the appropriate endpoints.



## API Usage Examples
Fill free to use 

> username: "user"

> password: "password"

for Spring Security Basic Auth.

### Create a Company

> POST /api/companies

Content-Type: application/json
```
{
  "name": "Example Company",
  "registration_number": "123456789",
  "address": "123 Main St"
}
```

### Get All Companies

> GET /api/companies

### Get Company by ID

> GET /api/companies/{companyId}

### Update Company

> PUT /api/companies/{companyId}

Content-Type: application/json
```
{
  "name": "Updated Company Name",
  "registration_number": "987654321",
  "address": "456 Elm St"
}
```

### Delete Company

> DELETE /api/companies/{companyId}

### Create a Report for a Company

> POST /api/companies/{companyId}/reports

Content-Type: application/json
```
{
  "report_date": "2024-06-09",
  "total_revenue": 100000.00,
  "net_profit": 50000.00
}
```

### Get All Reports for a Company

> GET /api/companies/{companyId}/reports

### Get Report Details

> GET /api/reports/{reportId}

### Update Report

> PUT /api/reports/{reportId}

Content-Type: application/json
```
{
  "report_date": "2024-06-10",
  "total_revenue": 150000.00,
  "net_profit": 75000.00
}
```

### Delete Report

> DELETE /api/reports/{reportId}
