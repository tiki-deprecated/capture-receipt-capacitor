package com.mytiki.sdk.capture.receipt.capacitor

enum class RetailerEnum (val value: Int){
    ACME_MARKETS(com.microblink.linking.ACME_MARKETS),
    ALBERTSONS(com.microblink.linking.ALBERTSONS),
    AMAZON(com.microblink.linking.AMAZON_BETA),
    AMAZON_CA(com.microblink.linking.AMAZON_CA_BETA),
    AMAZON_UK(com.microblink.linking.AMAZON_UK_BETA),
    BED_BATH_AND_BEYOND(com.microblink.linking.BED_BATH_AND_BEYOND),
    BESTBUY(com.microblink.linking.BESTBUY),
    BJS_WHOLESALE(com.microblink.linking.BJS_WHOLESALE),
    CHEWY(com.microblink.linking.CHEWY),
    COSTCO(com.microblink.linking.COSTCO),
    CVS(com.microblink.linking.CVS),
    DICKS_SPORTING_GOODS(com.microblink.linking.DICKS_SPORTING_GOODS),
    DOLLAR_GENERAL(com.microblink.linking.DOLLAR_GENERAL),
    DOLLAR_TREE(com.microblink.linking.DOLLAR_TREE),
    DOMINOS_PIZZA(com.microblink.linking.DOMINOS_PIZZA),
    DOOR_DASH(com.microblink.linking.DOOR_DASH),
    DRIZLY(com.microblink.linking.DRIZLY),
    FAMILY_DOLLAR(com.microblink.linking.FAMILY_DOLLAR),
    FOOD_4_LESS(com.microblink.linking.FOOD_4_LESS),
    FOOD_LION(com.microblink.linking.FOOD_LION),
    FRED_MEYER(com.microblink.linking.FRED_MEYER),
    GAP(com.microblink.linking.GAP),
    GIANT_EAGLE(com.microblink.linking.GIANT_EAGLE),
    GRUBHUB(com.microblink.linking.GRUBHUB),
    HEB(com.microblink.linking.HEB),
    HOME_DEPOT(com.microblink.linking.HOME_DEPOT),
    HYVEE(com.microblink.linking.HYVEE),
    INSTACART(com.microblink.linking.INSTACART),
    JEWEL_OSCO(com.microblink.linking.JEWEL_OSCO),
    KOHLS(com.microblink.linking.KOHLS),
    KROGER(com.microblink.linking.KROGER),
    LOWES(com.microblink.linking.LOWES),
    MACYS(com.microblink.linking.MACYS),
    MARSHALLS(com.microblink.linking.MARSHALLS),
    MEIJER(com.microblink.linking.MEIJER),
    NIKE(com.microblink.linking.NIKE),
    PUBLIX(com.microblink.linking.PUBLIX),
    RALPHS(com.microblink.linking.RALPHS),
    RITE_AID(com.microblink.linking.RITE_AID),
    SAFEWAY(com.microblink.linking.SAFEWAY),
    SAMS_CLUB(com.microblink.linking.SAMS_CLUB),
    SEAMLESS(com.microblink.linking.SEAMLESS),
    SEPHORA(com.microblink.linking.SEPHORA),
    SHIPT(com.microblink.linking.SHIPT),
    SHOPRITE(com.microblink.linking.SHOPRITE),
    SPROUTS(com.microblink.linking.SPROUTS),
    STAPLES(com.microblink.linking.STAPLES),
    STAPLES_CA(com.microblink.linking.STAPLES_CA),
    STARBUCKS(com.microblink.linking.STARBUCKS),
    TACO_BELL(com.microblink.linking.TACO_BELL),
    TARGET(com.microblink.linking.TARGET),
    TJ_MAXX(com.microblink.linking.TJ_MAXX),
    UBER_EATS(com.microblink.linking.UBER_EATS),
    ULTA(com.microblink.linking.ULTA),
    VONS(com.microblink.linking.VONS),
    WALGREENS(com.microblink.linking.WALGREENS),
    WALMART(com.microblink.linking.WALMART),
    WALMART_CA(com.microblink.linking.WALMART_CA),
    WEGMANS(com.microblink.linking.WEGMANS);

    fun toMbInt() : Int = this.value
    override fun toString() = this.name

    companion object {
        fun fromMbInt(intValue: Int) = values().first { it.value == intValue }
        fun fromString(stringValue: String) = values().first { it.name == stringValue }

    }
}
