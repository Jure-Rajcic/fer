module.exports = class Helper {

    constructor(params) {
        this.params = params;
    }


    email() { // zbog konteksta zadatka
        return this.params.email ? this.params.email : "" 
    }

    newsletters() { // dupli razmaci medu ricima nisu cool 
        return ['None, thank you', 'All about seeds', 'Newest trends in gardening']; 
    }

    isNewsletterSelected(val) {
        return this.params.newsletter == val ? "selected" : undefined
    }

    statements() {
        return ["I accept the terms and conditions", "I have read the privacy policy"]
    }

    isStatementSelected(val) {
        return this.params.statements && this.params.statements.includes(val) ? "checked" : undefined
    }

    stringToHTMLId(val) {
        return String(val).split(' ').join('-').toLowerCase();
    }

}
