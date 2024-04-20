const express = require('express');
const router = express.Router();

router.get('/', function (req, res, next) {
    //####################### ZADATAK #######################

    req.session.cart.destroy  // - obrisati sadržaj košarice
    req.session.user.destroy  // - odjaviti registriranog korisnika iz sustava
    req.session.destroy((err) => {
        if (err) console.log(err)
    })
    res.redirect('/')  // - napraviti redirect na osnovnu stranicu
    //#######################################################
});

module.exports = router;