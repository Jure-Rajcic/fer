//uvoz modula
const express = require('express');
const app = express();
const path = require('path');
const pg = require('pg')
const db = require('./db')
const session = require('express-session')
const pgSession = require('connect-pg-simple')(session)

//uvoz modula s definiranom funkcionalnosti ruta
console.log(1)  	
const cartRoute = require('./routes/cart.routes');
console.log(4)
const checkoutRoute = require('./routes/checkout.routes');
const homeRouter = require('./routes/home.routes');
const loginRoute = require('./routes/login.routes');
const logoutRoute = require('./routes/logout.routes');
const orderRouter = require('./routes/order.routes');
const signupRoute = require('./routes/signup.routes');
const userRoute = require('./routes/user.routes');

//##############################################################
const onsiteRoute = require('./routes/on-site.routes');
//##############################################################


//middleware - predlošci (ejs)
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

//middleware - statički resursi
app.use(express.static(path.join(__dirname, 'public')));

//middleware - dekodiranje parametara
app.use(express.urlencoded({ extended: true }));

//####################### ZADATAK #######################

//pohrana sjednica u postgres bazu korštenjem connect-pg-simple modula
app.use(session({
    store: new pgSession({
        pool: db.pool,
    }), // stores the session data on the server and gives the client a session ID to access the session data
    secret: "web-lab4", // secret is a key used for encrypting cookies
    resave: false, // for every request to the server, it DOESNT reset the session cookie
    saveUninitialized: true // When an empty session object is created and no properties are set, it is the uninitialized state
}))

const cartInterface = require('./models/CartModel')
console.log(cartInterface)

// stvaranje kosarice ako je user nie logiran
app.use((req, res, next) => {
    console.log(req.session) // refresh na home pageu dok si ulogiran
    if (req.session.cart === undefined || req.session.cart.invalid) {
        req.session.cart = cartInterface.createCart();
    }
    next(); // bez ovog se sve freeza ako nisam ulogiran
})

//#######################################################


//definicija ruta
app.use('/', homeRouter);
app.use('/order', orderRouter);
app.use('/login', loginRoute);
app.use('/logout', logoutRoute);
app.use('/signup', signupRoute);
app.use('/cart', cartRoute);
app.use('/user', userRoute);
app.use('/checkout', checkoutRoute);

//##############################################################
app.use('/on-site', onsiteRoute);
//##############################################################


//pokretanje poslužitelja na portu 3000
app.listen(3000);

