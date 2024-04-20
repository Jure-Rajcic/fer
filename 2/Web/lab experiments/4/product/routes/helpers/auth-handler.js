function authHandler(req, res, next) {
    //ako korisnik nije logiran, redirekcija na osnovnu stranicu
    if (req.session.user === undefined) { // dodat nekakav kod ako je cart prazan da npr stavi u kosaricu?
        console.log("Redirecting user to login");
        req.session.err = "Please login to view the requested page."
        req.session.save(() => {
            //redirekcija na osnovnu stranicu
            res.redirect('/login');
        });
    } else {
        console.log("User data present");
        next();
    }


}
module.exports = authHandler;