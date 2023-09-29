package com.mytiki.sdk.capture.receipt.capacitor

/**
 * Enum of all account providers.
 *
 * This enum lists all the account providers supported by the system.
 *
 * @property type The type of the provider, which can be either Email or Retailer.
 * @property source The source of the provider.
 * @constructor Creates an empty [AccountCommon] enum entry.
 */
enum class AccountCommon(val type: AccountTypeEnum, val source: String) {
    ACME_MARKETS(AccountTypeEnum.RETAILER, RetailerEnum.ACME_MARKETS.toString()),
    ALBERTSONS(AccountTypeEnum.RETAILER, RetailerEnum.ALBERTSONS.toString()),
    AMAZON(AccountTypeEnum.RETAILER, RetailerEnum.AMAZON.toString()),
    AMAZON_CA(AccountTypeEnum.RETAILER, RetailerEnum.AMAZON_CA.toString()),
    AMAZON_UK(AccountTypeEnum.RETAILER, RetailerEnum.AMAZON_UK.toString()),
    BED_BATH_AND_BEYOND(AccountTypeEnum.RETAILER, RetailerEnum.BED_BATH_AND_BEYOND.toString()),
    BESTBUY(AccountTypeEnum.RETAILER, RetailerEnum.BESTBUY.toString()),
    BJS_WHOLESALE(AccountTypeEnum.RETAILER, RetailerEnum.BJS_WHOLESALE.toString()),
    CHEWY(AccountTypeEnum.RETAILER, RetailerEnum.CHEWY.toString()),
    COSTCO(AccountTypeEnum.RETAILER, RetailerEnum.COSTCO.toString()),
    CVS(AccountTypeEnum.RETAILER, RetailerEnum.CVS.toString()),
    DICKS_SPORTING_GOODS(AccountTypeEnum.RETAILER, RetailerEnum.DICKS_SPORTING_GOODS.toString()),
    DOLLAR_GENERAL(AccountTypeEnum.RETAILER, RetailerEnum.DOLLAR_GENERAL.toString()),
    DOLLAR_TREE(AccountTypeEnum.RETAILER, RetailerEnum.DOLLAR_TREE.toString()),
    DOMINOS_PIZZA(AccountTypeEnum.RETAILER, RetailerEnum.DOMINOS_PIZZA.toString()),
    DOOR_DASH(AccountTypeEnum.RETAILER, RetailerEnum.DOOR_DASH.toString()),
    DRIZLY(AccountTypeEnum.RETAILER, RetailerEnum.DRIZLY.toString()),
    FAMILY_DOLLAR(AccountTypeEnum.RETAILER, RetailerEnum.FAMILY_DOLLAR.toString()),
    FOOD_4_LESS(AccountTypeEnum.RETAILER, RetailerEnum.FOOD_4_LESS.toString()),
    FOOD_LION(AccountTypeEnum.RETAILER, RetailerEnum.FOOD_LION.toString()),
    FRED_MEYER(AccountTypeEnum.RETAILER, RetailerEnum.FRED_MEYER.toString()),
    GAP(AccountTypeEnum.RETAILER, RetailerEnum.GAP.toString()),
    GIANT_EAGLE(AccountTypeEnum.RETAILER, RetailerEnum.GIANT_EAGLE.toString()),
    GRUBHUB(AccountTypeEnum.RETAILER, RetailerEnum.GRUBHUB.toString()),
    HEB(AccountTypeEnum.RETAILER, RetailerEnum.HEB.toString()),
    HOME_DEPOT(AccountTypeEnum.RETAILER, RetailerEnum.HOME_DEPOT.toString()),
    HYVEE(AccountTypeEnum.RETAILER, RetailerEnum.HYVEE.toString()),
    INSTACART(AccountTypeEnum.RETAILER, RetailerEnum.INSTACART.toString()),
    JEWEL_OSCO(AccountTypeEnum.RETAILER, RetailerEnum.JEWEL_OSCO.toString()),
    KOHLS(AccountTypeEnum.RETAILER, RetailerEnum.KOHLS.toString()),
    KROGER(AccountTypeEnum.RETAILER, RetailerEnum.KROGER.toString()),
    LOWES(AccountTypeEnum.RETAILER, RetailerEnum.LOWES.toString()),
    MACYS(AccountTypeEnum.RETAILER, RetailerEnum.MACYS.toString()),
    MARSHALLS(AccountTypeEnum.RETAILER, RetailerEnum.MARSHALLS.toString()),
    MEIJER(AccountTypeEnum.RETAILER, RetailerEnum.MEIJER.toString()),
    NIKE(AccountTypeEnum.RETAILER, RetailerEnum.NIKE.toString()),
    PUBLIX(AccountTypeEnum.RETAILER, RetailerEnum.PUBLIX.toString()),
    RALPHS(AccountTypeEnum.RETAILER, RetailerEnum.RALPHS.toString()),
    RITE_AID(AccountTypeEnum.RETAILER, RetailerEnum.RITE_AID.toString()),
    SAFEWAY(AccountTypeEnum.RETAILER, RetailerEnum.SAFEWAY.toString()),
    SAMS_CLUB(AccountTypeEnum.RETAILER, RetailerEnum.SAMS_CLUB.toString()),
    SEAMLESS(AccountTypeEnum.RETAILER, RetailerEnum.SEAMLESS.toString()),
    SEPHORA(AccountTypeEnum.RETAILER, RetailerEnum.SEPHORA.toString()),
    SHIPT(AccountTypeEnum.RETAILER, RetailerEnum.SHIPT.toString()),
    SHOPRITE(AccountTypeEnum.RETAILER, RetailerEnum.SHOPRITE.toString()),
    SPROUTS(AccountTypeEnum.RETAILER, RetailerEnum.SPROUTS.toString()),
    STAPLES(AccountTypeEnum.RETAILER, RetailerEnum.STAPLES.toString()),
    STAPLES_CA(AccountTypeEnum.RETAILER, RetailerEnum.STAPLES_CA.toString()),
    STARBUCKS(AccountTypeEnum.RETAILER, RetailerEnum.STARBUCKS.toString()),
    TACO_BELL(AccountTypeEnum.RETAILER, RetailerEnum.TACO_BELL.toString()),
    TARGET(AccountTypeEnum.RETAILER, RetailerEnum.TARGET.toString()),
    TJ_MAXX(AccountTypeEnum.RETAILER, RetailerEnum.TJ_MAXX.toString()),
    UBER_EATS(AccountTypeEnum.RETAILER, RetailerEnum.UBER_EATS.toString()),
    ULTA(AccountTypeEnum.RETAILER, RetailerEnum.ULTA.toString()),
    VONS(AccountTypeEnum.RETAILER, RetailerEnum.VONS.toString()),
    WALGREENS(AccountTypeEnum.RETAILER, RetailerEnum.WALGREENS.toString()),
    WALMART(AccountTypeEnum.RETAILER, RetailerEnum.WALMART.toString()),
    WALMART_CA(AccountTypeEnum.RETAILER, RetailerEnum.WALMART_CA.toString()),
    WEGMANS(AccountTypeEnum.RETAILER, RetailerEnum.WEGMANS.toString()),
    YAHOO(AccountTypeEnum.EMAIL, EmailEnum.YAHOO.toString()),
    OUTLOOK(AccountTypeEnum.EMAIL, EmailEnum.OUTLOOK.toString()),
    AOL(AccountTypeEnum.EMAIL, EmailEnum.AOL.toString()),
    GMAIL(AccountTypeEnum.EMAIL, EmailEnum.GMAIL.toString());


    /**
     * Converts the [AccountCommon] object into a string representation.
     *
     * @return The name of the enum entry as a string.
     */
    override fun toString() = this.name

    companion object {
        /**
         * Finds and returns an [AccountCommon] enum entry with the specified [stringValue].
         *
         * @param stringValue The [AccountCommon.source] to search for.
         * @return The matching [AccountCommon] enum entry, or null if not found.
         */
        fun fromSource(source: String) = AccountCommon.values().first { it.source == source }

    }

}