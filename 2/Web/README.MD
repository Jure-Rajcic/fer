# Web 📂

### 📘 Lectures (🇭🇷)
- 00-Organizacija.pdf
- 01-Uvod.pdf
- 02-HTML_1od2.pdf
- 02-HTML_20d2.pdf
- 03-CSS_1od2.pdf
- 04-CSS_20d2.pdf
- 05-JavaScript_1od3.pdf
- 06-JavaScript_20d3.pdf
- 07-Javascript_30d3.pdf
- 08-HTTP.pdf
- 09-DYN-WEB-1-NodeJs_Postgres.pdf
- 10-DYN-WEB-2-NodeJs_MVC.pdf
- 11-DYN-WEB-3-Sjednice.pdf
- 12-DYN-WEB-4-WebServer.pdf
- 13-WEB-Search.pdf


### 🔬 Lab Experiments (🇭🇷)

This course includes various lab experiments. Please note that these labs are not hosted on a server due to the backend and database requirements, and because they are not monetized. Below are some snapshots of the experiments:

- [View Image 1](https://ibb.co/c6Z3Xnp)
- [View Image 2](https://ibb.co/rsRdYsy)
- [View Image 3](https://ibb.co/vk4F1fX)
- [View Image 4](https://ibb.co/M5FD3S2)
- [View Image 5](https://ibb.co/4ZH552w)
- [View Image 6](https://ibb.co/RHNKT4C)

To run the last solution locally, your application will operate on port 3000. Ensure that PostgreSQL is configured properly with the following settings:

```javascript
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'web-lab4',
  password: 'bazepodataka',
  port: 5432,
});
```

Before running the application, install the necessary dependencies using:

```bash
npm install
```

To start the development server, execute:

```bash
npm run dev
```
