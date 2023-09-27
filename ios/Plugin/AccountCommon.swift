/*
 * AccountCommon Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation

/// A Swift struct representing common properties of an account, such as its type and source.
public struct AccountCommon{
    
    /// The type of the account, represented by an AccountTypeEnum.
    let type: AccountTypeEnum
    /// The source of the account, typically a string identifier.
    let source: String
    
    /// A dictionary of default AccountCommon objects for various sources.
    static let defaults: [String: AccountCommon] = [
        "ACME_MARKETS" : AccountCommon(type: .retailer, source: RetailerEnum.ACME_MARKETS.rawValue),
        "ALBERTSONS" : AccountCommon(type: .retailer, source: RetailerEnum.ALBERTSONS.rawValue),
        "AMAZON": AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_BETA.rawValue),
        "AMAZON_CA": AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_CA.rawValue),
        "AMAZON_UK": AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_UK.rawValue),
        "BED_BATH_AND_BEYOND": AccountCommon(type: .retailer, source: RetailerEnum.BED_BATH_AND_BEYOND.rawValue),
        "BESTBUY": AccountCommon(type: .retailer, source: RetailerEnum.BESTBUY.rawValue),
        "BJS_WHOLESALE": AccountCommon(type: .retailer, source: RetailerEnum.BJS_WHOLESALE.rawValue),
        "CHEWY": AccountCommon(type: .retailer, source: RetailerEnum.CHEWY.rawValue),
        "COSTCO": AccountCommon(type: .retailer, source: RetailerEnum.COSTCO.rawValue),
        "CVS": AccountCommon(type: .retailer, source: RetailerEnum.CVS.rawValue),
        "DICKS_SPORTING_GOODS": AccountCommon(type: .retailer, source: RetailerEnum.DICKS_SPORTING_GOODS.rawValue),
        "DOLLAR_GENERAL": AccountCommon(type: .retailer, source: RetailerEnum.DOLLAR_GENERAL.rawValue),
        "DOLLAR_TREE": AccountCommon(type: .retailer, source: RetailerEnum.DOLLAR_TREE.rawValue),
        "DOMINOS_PIZZA": AccountCommon(type: .retailer, source: RetailerEnum.DOMINOS_PIZZA.rawValue),
        "DOOR_DASH": AccountCommon(type: .retailer, source: RetailerEnum.DOOR_DASH.rawValue),
        "DRIZLY": AccountCommon(type: .retailer, source: RetailerEnum.DRIZLY.rawValue),
        "FAMILY_DOLLAR": AccountCommon(type: .retailer, source: RetailerEnum.FAMILY_DOLLAR.rawValue),
        "FOOD_4_LESS": AccountCommon(type: .retailer, source: RetailerEnum.FOOD_4_LESS.rawValue),
        "FOOD_LION": AccountCommon(type: .retailer, source: RetailerEnum.FOOD_LION.rawValue),
        "FRED_MEYER": AccountCommon(type: .retailer, source: RetailerEnum.FRED_MEYER.rawValue),
        "GAP": AccountCommon(type: .retailer, source: RetailerEnum.GAP.rawValue),
        "GIANT_EAGLE": AccountCommon(type: .retailer, source: RetailerEnum.GIANT_EAGLE.rawValue),
        "GRUBHUB": AccountCommon(type: .retailer, source: RetailerEnum.GRUBHUB.rawValue),
        "HEB": AccountCommon(type: .retailer, source: RetailerEnum.HEB.rawValue),
        "HOME_DEPOT": AccountCommon(type: .retailer, source: RetailerEnum.HOME_DEPOT.rawValue),
        "HYVEE": AccountCommon(type: .retailer, source: RetailerEnum.HYVEE.rawValue),
        "INSTACART": AccountCommon(type: .retailer, source: RetailerEnum.INSTACART.rawValue),
        "JEWEL_OSCO": AccountCommon(type: .retailer, source: RetailerEnum.JEWEL_OSCO.rawValue),
        "KOHLS": AccountCommon(type: .retailer, source: RetailerEnum.KOHLS.rawValue),
        "KROGER": AccountCommon(type: .retailer, source: RetailerEnum.KROGER.rawValue),
        "LOWES": AccountCommon(type: .retailer, source: RetailerEnum.LOWES.rawValue),
        "MACYS": AccountCommon(type: .retailer, source: RetailerEnum.MACYS.rawValue),
        "MARSHALLS": AccountCommon(type: .retailer, source: RetailerEnum.MARSHALLS.rawValue),
        "MEIJER": AccountCommon(type: .retailer, source: RetailerEnum.MEIJER.rawValue),
        "NIKE": AccountCommon(type: .retailer, source: RetailerEnum.NIKE.rawValue),
        "PUBLIX": AccountCommon(type: .retailer, source: RetailerEnum.PUBLIX.rawValue),
        "RALPHS": AccountCommon(type: .retailer, source: RetailerEnum.RALPHS.rawValue),
        "RITE_AID": AccountCommon(type: .retailer, source: RetailerEnum.RITE_AID.rawValue),
        "SAFEWAY": AccountCommon(type: .retailer, source: RetailerEnum.SAFEWAY.rawValue),
        "SAMS_CLUB": AccountCommon(type: .retailer, source: RetailerEnum.SAMS_CLUB.rawValue),
        "SEAMLESS": AccountCommon(type: .retailer, source: RetailerEnum.SEAMLESS.rawValue),
        "SEPHORA": AccountCommon(type: .retailer, source: RetailerEnum.SEPHORA.rawValue),
        "SHIPT": AccountCommon(type: .retailer, source: RetailerEnum.SHIPT.rawValue),
        "SHOPRITE": AccountCommon(type: .retailer, source: RetailerEnum.SHOPRITE.rawValue),
        "SPROUTS": AccountCommon(type: .retailer, source: RetailerEnum.SPROUTS.rawValue),
        "STAPLES": AccountCommon(type: .retailer, source: RetailerEnum.STAPLES.rawValue),
        "STAPLES_CA": AccountCommon(type: .retailer, source: RetailerEnum.STAPLES.rawValue),
        "STARBUCKS": AccountCommon(type: .retailer, source: RetailerEnum.STARBUCKS.rawValue),
        "TACO_BELL": AccountCommon(type: .retailer, source: RetailerEnum.TACO_BELL.rawValue),
        "TARGET": AccountCommon(type: .retailer, source: RetailerEnum.TARGET.rawValue),
        "TJ_MAXX": AccountCommon(type: .retailer, source: RetailerEnum.TJ_MAXX.rawValue),
        "UBER_EATS": AccountCommon(type: .retailer, source: RetailerEnum.UBER_EATS.rawValue),
        "ULTA": AccountCommon(type: .retailer, source: RetailerEnum.ULTA.rawValue),
        "VONS": AccountCommon(type: .retailer, source: RetailerEnum.VONS.rawValue),
        "WALGREENS": AccountCommon(type: .retailer, source: RetailerEnum.WALGREENS.rawValue),
        "WALMART": AccountCommon(type: .retailer, source: RetailerEnum.WALMART.rawValue),
        "WALMART_CA": AccountCommon(type: .retailer, source: RetailerEnum.WALMART_CA.rawValue),
        "WEGMANS": AccountCommon(type: .retailer, source: RetailerEnum.WEGMANS.rawValue),
        "YAHOO" : AccountCommon(type: .email, source: EmailEnum.YAHOO.rawValue),
        "OUTLOOK" : AccountCommon(type: .email, source: EmailEnum.OUTLOOK.rawValue),
        "AOL" : AccountCommon(type: .email, source: EmailEnum.AOL.rawValue),
        "GMAIL" : AccountCommon(type: .email, source: EmailEnum.GMAIL.rawValue),
        "GMAILIMAP" : AccountCommon(type: .email, source: EmailEnum.GMAIL.rawValue)
    ]
}
