# PrestaFind

PrestaFind is a web application designed to help users find and manage service offers. It provides an easy-to-use interface for searching, filtering, and exploring PrestaShop modules to enhance your e-commerce store.

---

## Features

- **Search Modules**: Quickly search for PrestaShop modules by name, category, or keyword.
- **Filter Results**: Filter modules by compatibility, rating, or price.
- **Module Details**: View detailed information about each module, including descriptions, screenshots, and user reviews.
- **User-Friendly Interface**: Intuitive and responsive design for seamless navigation.
- **Admin Panel**: Manage modules, categories, and user feedback through an admin dashboard.

---

## Technologies Used

- **Frontend**: HTML, CSS, JavaScript, Bootstrap
- **Backend**: PHP, MySQL
- **Framework**: PrestaShop
- **Other Tools**: Git, Composer

---

## Installation

Follow these steps to set up PrestaFind locally:

### Prerequisites
- PHP (>= 7.0)
- MySQL
- Composer
- Git

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Aziz16KH/prestafind.git
   cd prestafind
   ```
2. Install dependencies using Composer:
   ```bash
   composer install
   ```
3. Set up the database:
   - Create a new MySQL database.
   - Import the provided SQL file (`database/prestafind.sql`) into your database.
4. Configure the `.env` file:
   - Rename `.env.example` to `.env`.
   - Update the database credentials:
     ```env
     DB_HOST=localhost
     DB_NAME=prestafind
     DB_USER=root
     DB_PASSWORD=yourpassword
     ```
5. Start the development server:
   ```bash
   php -S localhost:8000 -t public
   ```
6. Open your browser and navigate to `http://localhost:8000`.

---

## Usage

1. **Search for Modules**:
   - Enter a keyword in the search bar and press Enter.
   - Use filters to narrow down results by category, rating, or price.

2. **View Module Details**:
   - Click on a module to view its details, including description, screenshots, and reviews.

3. **Admin Panel**:
   - Access the admin dashboard at `/admin`.
   - Log in with the provided admin credentials (check the database for default admin details).
   - Manage modules, categories, and user feedback.
---

## Contributing

Contributions are welcome! If you'd like to contribute to PrestaFind, follow these steps:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Make your changes and commit them:
   ```bash
   git commit -m "Add your message here"
   ```
4. Push to the branch:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Open a pull request and describe your changes.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Acknowledgments

- Special thanks to all collaborators who helped improve this project.

---

## Contact

For questions or feedback, feel free to reach out:

- **Author**: Aziz KH
- **Email**: khitmiaziz@gmail.com
- **GitHub**: [Aziz16KH](https://github.com/Aziz16KH)
