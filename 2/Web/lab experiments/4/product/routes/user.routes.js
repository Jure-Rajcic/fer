const express = require('express');
const router = express.Router();
const Address = require('../models/AddressModel')
const Order = require('../models/OrderModel')
const authHandler = require('./helpers/auth-handler');

//prikaz podataka o korisniku (podaci o korisniku, adrese, narudžbe)
// Ulančavanje funkcija međuopreme
router.get('/', authHandler, function (req, res, next) {
    (async () => {
        
        res.render('user', {
            title: 'User profile',
            user: req.session.user,
            address: (await Address.fetchByUser(req.session.user))[0], //dobavi adresu korisnika
            orders: await Order.fetchByUser(req.session.user), //dobavi narudžbe korisnika
            linkActive: 'user'
        });
    })()
});

module.exports = router;