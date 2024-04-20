const express = require('express');
const router = express.Router();
const Helper = require('./helpers/helper');
const authHandler = require('./helpers/auth-handler');
const { body } = require('express-validator');



router.get('/', authHandler,  function (req, res, next) {
    if (!req.session.params) {
        req.session.params = {};
    }
    res.render('view', {
        title: 'Delivery Options',
        linkActive: 'cart',
        user: req.session.user,
        helper: new Helper(req.session.params) 
    });
});

router.post('/save', function (req, res, next) {
    //console.log("REQUESTttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
    //console.log(req.body)
    req.session.params.email = req.body['e-mail'] ? req.body['e-mail'] : req.session.user.email
    req.session.params.newsletter = req.body.newsletters;
    req.session.params.statements = req.body.statements; // jer chekbox moze biti undefined
    //console.log(req.session.params)
    res.redirect('/cart');
});

router.post('/reset', function (req, res, next) {
    req.session.params = undefined; // obrisati formu
    res.redirect('/on-site');
});

router.post('/order', function (req, res, next) {
    res.redirect('/checkout'); // jer brise kosaricu i posatavlja ostale atribute 
})

module.exports = router;