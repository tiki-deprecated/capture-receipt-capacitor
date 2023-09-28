/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from './account-type';
import * as Type from './types';

class AccountTypes {
  private readonly index: Map<string, AccountType> = new Map([
    [Type.ACME_MARKETS.id, Type.ACME_MARKETS],
    [Type.ALBERTSONS.id, Type.ALBERTSONS],
    [Type.AMAZON.id, Type.AMAZON],
    [Type.AMAZON_CA.id, Type.AMAZON_CA],
    [Type.AMAZON_UK.id, Type.AMAZON_UK],
    [Type.AOL.id, Type.AOL],
    [Type.BED_BATH_AND_BEYOND.id, Type.BED_BATH_AND_BEYOND],
    [Type.BEST_BUY.id, Type.BEST_BUY],
    [Type.BJS_WHOLESALE.id, Type.BJS_WHOLESALE],
    [Type.CHEWY.id, Type.CHEWY],
    [Type.COSTCO.id, Type.COSTCO],
    [Type.CVS.id, Type.CVS],
    [Type.DICKS_SPORTING_GOODS.id, Type.DICKS_SPORTING_GOODS],
    [Type.DOLLAR_GENERAL.id, Type.DOLLAR_GENERAL],
    [Type.DOLLAR_TREE.id, Type.DOLLAR_TREE],
    [Type.DOMINOS_PIZZA.id, Type.DOMINOS_PIZZA],
    [Type.DOOR_DASH.id, Type.DOOR_DASH],
    [Type.DRIZLY.id, Type.DRIZLY],
    [Type.FAMILY_DOLLAR.id, Type.FAMILY_DOLLAR],
    [Type.FOOD_4_LESS.id, Type.FOOD_4_LESS],
    [Type.FOOD_LION.id, Type.FOOD_LION],
    [Type.FRED_MEYER.id, Type.FRED_MEYER],
    [Type.GAP.id, Type.GAP],
    [Type.GIANT_EAGLE.id, Type.GIANT_EAGLE],
    [Type.GMAIL.id, Type.GMAIL],
    [Type.GRUBHUB.id, Type.GRUBHUB],
    [Type.HARRIS_TEETER.id, Type.HARRIS_TEETER],
    [Type.HEB.id, Type.HEB],
    [Type.HOME_DEPOT.id, Type.HOME_DEPOT],
    [Type.HY_VEE.id, Type.HY_VEE],
    [Type.INSTACART.id, Type.INSTACART],
    [Type.JEWEL_OSCO.id, Type.JEWEL_OSCO],
    [Type.KOHLS.id, Type.KOHLS],
    [Type.KROGER.id, Type.KROGER],
    [Type.LOWES.id, Type.LOWES],
    [Type.MACYS.id, Type.MACYS],
    [Type.MARSHALLS.id, Type.MARSHALLS],
    [Type.MEIJER.id, Type.MEIJER],
    [Type.NIKE.id, Type.NIKE],
    [Type.OUTLOOK.id, Type.OUTLOOK],
    [Type.PUBLIX.id, Type.PUBLIX],
    [Type.RALPHS.id, Type.RALPHS],
    [Type.RITE_AID.id, Type.RITE_AID],
    [Type.SAFEWAY.id, Type.SAFEWAY],
    [Type.SAMS_CLUB.id, Type.SAMS_CLUB],
    [Type.SEAMLESS.id, Type.SEAMLESS],
    [Type.SEPHORA.id, Type.SEPHORA],
    [Type.SHIPT.id, Type.SHIPT],
    [Type.SHOP_RITE.id, Type.SHOP_RITE],
    [Type.SPROUTS.id, Type.SPROUTS],
    [Type.STAPLES.id, Type.STAPLES],
    [Type.STAPLES_CA.id, Type.STAPLES_CA],
    [Type.STARBUCKS.id, Type.STARBUCKS],
    [Type.TACO_BELL.id, Type.TACO_BELL],
    [Type.TARGET.id, Type.TARGET],
    [Type.TJ_MAXX.id, Type.TJ_MAXX],
    [Type.UBER_EATS.id, Type.UBER_EATS],
    [Type.ULTA.id, Type.ULTA],
    [Type.VONS.id, Type.VONS],
    [Type.WALGREENS.id, Type.WALGREENS],
    [Type.WALMART.id, Type.WALMART],
    [Type.WALMART_CA.id, Type.WALMART_CA],
    [Type.WEGMANS.id, Type.WEGMANS],
    [Type.YAHOO.id, Type.YAHOO],
  ]);

  get ids(): string[] {
    return Array.from(this.index.keys());
  }

  from(id: string): AccountType | undefined {
    return this.index.get(id);
  }
}

export const accountTypes = new AccountTypes();
