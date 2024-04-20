const express = require('express');
const router = express.Router();
const User = require('../models/UserModel')


router.get('/', function (req, res, next) {
    //####################### ZADATAK #######################
    //vrati login stranicu
    console.log(req.session)
    let err = req.session.err
    req.session.err = undefined
    res.render('login', {
        title: 'Login',
        linkActive: 'login',
        user: req.session.user,
        err: err
    })
    //#######################################################

});

router.post('/', async function (req, res, next) {
    //####################### ZADATAK #######################
    (async () => {
        //postupak prijave korisnika
        let user = await User.fetchByUsername(req.body.user)
        console.log(user)
        if (user.id !== undefined && user.checkPassword(req.body.password)) {
            req.session.user = user
            res.redirect('/')
        } else {
            res.render('login', {
                title: 'Login',
                linkActive: 'login',
                user: req.session.user,
                err: 'User does not exist or incorrect password.'
            })
        }
    })()
    //#######################################################
});


module.exports = router;