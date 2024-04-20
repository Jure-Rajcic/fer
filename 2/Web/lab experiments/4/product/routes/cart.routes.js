const express = require('express');
const router = express.Router();
const cartInterface = require('../models/CartModel') 
console.log(cartInterface)
const cartSanitizer = require('./helpers/cart-sanitizer');

// Ulančavanje funkcija međuopreme
router.get('/', cartSanitizer, function (req, res, next) {
    //####################### ZADATAK #######################
    console.log(req.session)
    // prikaz košarice uz pomoć cart.ejs
    res.render('cart', {
        title: 'Cart',
        linkActive: 'cart',
        user: req.session.user,
        cart: req.session.cart,
        err: undefined
    })

    //#######################################################
});


router.get('/add/:id', function (req, res, next) {
    
    //####################### ZADATAK #######################
    //dodavanje jednog artikla u košaricu
    // clicking +
    (async () => {
        await cartInterface.addItemToCart(req.session.cart, req.params.id, 1) // automatski refresh
        res.end() // ovisno jesmo li na order ili cart stranici zelimo prekinudti daljnji odziv kako bi ostali na toj stranice
        // tj nema res.render()
    })()
    //#######################################################
});

router.get('/remove/:id', async function (req, res, next) {
    //####################### ZADATAK #######################
    //brisanje jednog artikla iz košarice
    // clicking -
    (async () => {
        await cartInterface.removeItemFromCart(req.session.cart, req.params.id, 1)
        res.end()
    })()
    //#######################################################
});
console.log(2)
module.exports = router;
console.log(3)
