/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export default class AccountType {
  type: string
  icon: string
  name: string
  source: string

  findByKey = (key: string): AccountType | undefined => this.index.get(key);

  index: Map<string, AccountType> = new Map([
    [ALBERTSONS.key, ALBERTSONS],
    [AMAZON.key, AMAZON],
    [AMAZON_CA.key, AMAZON_CA],
    [AMAZON_UK.key, AMAZON_UK],
    [BED_BATH_AND_BEYOND.key, BED_BATH_AND_BEYOND],
    [GMAIL.key, GMAIL],
    [BESTBUY.key, BESTBUY],
    [BJS_WHOLESALE.key, BJS_WHOLESALE],
    [CHEWY.key, CHEWY],
    [COSTCO.key, COSTCO],
    [CVS.key, CVS],
    [DICKS_SPORTING_GOODS.key, DICKS_SPORTING_GOODS],
    [DOLLAR_GENERAL.key, DOLLAR_GENERAL],
    [DOLLAR_TREE.key, DOLLAR_TREE],
    [DOMINOS_PIZZA.key, DOMINOS_PIZZA],
    [DOOR_DASH.key, DOOR_DASH],
    [DRIZLY.key, DRIZLY],
    [FAMILY_DOLLAR.key, FAMILY_DOLLAR],
    [FOOD_4_LESS.key, FOOD_4_LESS],
    [FOOD_LION.key, FOOD_LION],
    [FRED_MEYER.key, FRED_MEYER],
    [GAP.key, GAP],
    [GIANT_EAGLE.key, GIANT_EAGLE],
    [GRUBHUB.key, GRUBHUB],
    [HARRIS_TEETER.key, HARRIS_TEETER],
    [HEB.key, HEB],
    [HOME_DEPOT.key, HOME_DEPOT],
    [HYVEE.key, HYVEE],
    [INSTACART.key, INSTACART],
    [JEWEL_OSCO.key, JEWEL_OSCO],
    [KOHLS.key, KOHLS],
    [KROGER.key, KROGER],
    [LOWES.key, LOWES],
    [MACYS.key, MACYS],
    [MARSHALLS.key, MARSHALLS],
    [MEIJER.key, MEIJER],
    [NIKE.key, NIKE],
    //[OUTLOOK.key, OUTLOOK],
    [RALPHS.key, RALPHS],
    [RITE_AID.key, RITE_AID],
    [SAFEWAY.key, SAFEWAY],
    [SAMS_CLUB.key, SAMS_CLUB],
    [SEAMLESS.key, SEAMLESS],
    [SEPHORA.key, SEPHORA],
    [SHIPT.key, SHIPT],
    [SHOPRITE.key, SHOPRITE],
    [SPROUTS.key, SPROUTS],
    [STAPLES.key, STAPLES],
    [STAPLES_CA.key, STAPLES_CA],
    [STARBUCKS.key, STARBUCKS],
    [TACO_BELL.key, TACO_BELL],
    [TARGET.key, TARGET],
    [TJ_MAXX.key, TJ_MAXX],
    [UBER_EATS.key, UBER_EATS],
    [ULTA.key, ULTA],
    [VONS.key, VONS],
    [WALGREENS.key, WALGREENS],
    [WALMART.key, WALMART],
    [WALMART_CA.key, WALMART_CA],
    [WEGMANS.key, WEGMANS],
  ]);
}
