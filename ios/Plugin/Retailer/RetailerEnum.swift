/*
 * RetailerEnum Enum
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkEReceipt
import Capacitor

/// An enumeration representing various retailer names as raw string values.
public enum RetailerEnum: String {
    
    // List of supported retailer names
    case ACME_MARKETS
    case ALBERTSONS
    case AMAZON
//    case AMAZON_BETA
    case AMAZON_CA
    case AMAZON_UK
    case BED_BATH_AND_BEYOND
    case GMAIL
    case BESTBUY
    case BJS_WHOLESALE
    case CHEWY
    case COSTCO
    case CVS
    case DICKS_SPORTING_GOODS
    case DOLLAR_GENERAL
    case DOLLAR_TREE
    case DOMINOS_PIZZA
    case DOOR_DASH
    case DRIZLY
    case FAMILY_DOLLAR
    case FOOD_4_LESS
    case FOOD_LION
    case FRED_MEYER
    case GAP
    case GIANT_EAGLE
    case GRUBHUB
    case HARRIS_TEETER
    case HEB
    case HOME_DEPOT
    case HYVEE
    case INSTACART
    case JEWEL_OSCO
    case KOHLS
    case KROGER
    case LOWES
    case MACYS
    case MARSHALLS
    case MEIJER
    case NIKE
    case PUBLIX
    case RALPHS
    case RITE_AID
    case SAFEWAY
    case SAMS_CLUB
    case SEAMLESS
    case SEPHORA
    case SHIPT
    case SHOPRITE
    case SPROUTS
    case STAPLES
    case STARBUCKS
    case TACO_BELL
    case TARGET
    case TJ_MAXX
    case UBER_EATS
    case ULTA
    case VONS
    case WALGREENS
    case WALMART
    case WALMART_CA
    case WEGMANS
    case POSTMATES
    
    
    static func fromBRAccountLinkingRetailer(_ ret: BRAccountLinkingRetailer) -> RetailerEnum {
        switch ret{
            case .acmeMarkets : return .ACME_MARKETS
            case .albertsons : return .ALBERTSONS
            case .amazon : return .AMAZON
            case .amazonBeta : return .AMAZON
            case .amazonBetaCA : return .AMAZON_CA
            case .amazonBetaUK : return .AMAZON_UK
            case .bedBath : return .BED_BATH_AND_BEYOND
            case .bestBuy : return .BESTBUY
            case .bjs : return .BJS_WHOLESALE
            case .chewy : return .CHEWY
            case .costco : return .COSTCO
            case .CVS : return .CVS
            case .dicksSportingGoods : return .DICKS_SPORTING_GOODS
            case .dollarGeneral : return .DOLLAR_GENERAL
            case .dollarTree : return .DOLLAR_TREE
            case .dominosPizza : return .DOMINOS_PIZZA
            case .doordash : return .DOOR_DASH
            case .drizly : return .DRIZLY
            case .familyDollar : return .FAMILY_DOLLAR
            case .food4Less : return .FOOD_4_LESS
            case .foodLion : return .FOOD_LION
            case .fredMeyer : return .FRED_MEYER
            case .gap : return .GAP
            case .giantEagle : return .GIANT_EAGLE
            case .grubhub : return .GRUBHUB
            case .harrisTeeter : return .HARRIS_TEETER
            case .HEB : return .HEB
            case .homeDepot : return .HOME_DEPOT
            case .hyVee : return .HYVEE
            case .instacart : return .INSTACART
            case .jewelOsco : return .JEWEL_OSCO
            case .kohls : return .KOHLS
            case .kroger : return .KROGER
            case .lowes : return .LOWES
            case .macys : return .MACYS
            case .marshalls : return .MARSHALLS
            case .meijer : return .MEIJER
            case .nike : return .NIKE
            case .publix : return .PUBLIX
            case .ralphs : return .RALPHS
            case .riteAid : return .RITE_AID
            case .safeway : return .SAFEWAY
            case .samsClub : return .SAMS_CLUB
            case .seamless : return .SEAMLESS
            case .sephora : return .SEPHORA
            case .shipt : return .SHIPT
            case .shoprite : return .SHOPRITE
            case .sprouts : return .SPROUTS
            case .staples : return .STAPLES
            case .starbucks : return .STARBUCKS
            case .tacoBell : return .TACO_BELL
            case .target : return .TARGET
            case .tjMaxx : return .TJ_MAXX
            case .uberEats : return .UBER_EATS
            case .ulta : return .ULTA
            case .vons : return .VONS
            case .walgreens : return .WALGREENS
            case .walmart : return .WALMART
            case .walmartCA : return .WALMART_CA
            case .wegmans : return .WEGMANS
            case .postmates: return .POSTMATES
        }
            
        
    }
    
    /// Converts a `RetailerEnum` value to its corresponding `BRAccountLinkingRetailer` representation.
    ///
    /// - Returns: The `BRAccountLinkingRetailer` associated with the `RetailerEnum` value.
func toBRAccountLinkingRetailer() -> BRAccountLinkingRetailer? {
        switch self{
            case .ACME_MARKETS : return .acmeMarkets
            case .ALBERTSONS : return .albertsons
            case .AMAZON : return .amazonBeta
//            case .AMAZON_BETA : return .amazonBeta
            case .AMAZON_CA : return .amazonBetaCA
            case .AMAZON_UK : return .amazonBetaUK
            case .BED_BATH_AND_BEYOND : return .bedBath
            case .BESTBUY : return .bestBuy
            case .BJS_WHOLESALE : return .bjs
            case .CHEWY : return .chewy
            case .COSTCO : return .costco
            case .CVS : return .CVS
            case .DICKS_SPORTING_GOODS : return .dicksSportingGoods
            case .DOLLAR_GENERAL : return .dollarGeneral
            case .DOLLAR_TREE : return .dollarTree
            case .DOMINOS_PIZZA : return .dominosPizza
            case .DOOR_DASH : return .doordash
            case .DRIZLY : return .drizly
            case .FAMILY_DOLLAR : return .familyDollar
            case .FOOD_4_LESS : return .food4Less
            case .FOOD_LION : return .foodLion
            case .FRED_MEYER : return .fredMeyer
            case .GAP : return .gap
            case .GIANT_EAGLE : return .giantEagle
            case .GMAIL : return nil
            case .GRUBHUB : return .grubhub
            case .HARRIS_TEETER : return .harrisTeeter
            case .HEB : return .HEB
            case .HOME_DEPOT : return .homeDepot
            case .HYVEE : return .hyVee
            case .INSTACART : return .instacart
            case .JEWEL_OSCO : return .jewelOsco
            case .KOHLS : return .kohls
            case .KROGER : return .kroger
            case .LOWES : return .lowes
            case .MACYS : return .macys
            case .MARSHALLS : return .marshalls
            case .MEIJER : return .meijer
            case .NIKE : return .nike
            case .PUBLIX : return .publix
            case .RALPHS : return .ralphs
            case .RITE_AID : return .riteAid
            case .SAFEWAY : return .safeway
            case .SAMS_CLUB : return .samsClub
            case .SEAMLESS : return .seamless
            case .SEPHORA : return .sephora
            case .SHIPT : return .shipt
            case .SHOPRITE : return .shoprite
            case .SPROUTS : return .sprouts
            case .STAPLES : return .staples
            case .STARBUCKS : return .starbucks
            case .TACO_BELL : return .tacoBell
            case .TARGET : return .target
            case .TJ_MAXX : return .tjMaxx
            case .UBER_EATS : return .uberEats
            case .ULTA : return .ulta
            case .VONS : return .vons
            case .WALGREENS : return .walgreens
            case .WALMART : return .walmart
            case .WALMART_CA : return .walmartCA
            case .WEGMANS : return .wegmans
            case .POSTMATES: return .postmates

        }
    }
    /// Converts a `BRAccountLinkingRetailer` value to its corresponding `RetailerEnum` representation.
    ///
    /// - Parameter retailer: The `BRAccountLinkingRetailer` value to convert.
    /// - Returns: The raw string value of the `RetailerEnum` associated with the `BRAccountLinkingRetailer`, or `nil` if not found.
    func toString() -> String? {
        switch self{
        case .ACME_MARKETS : return "ACME_MARKETS"
        case .ALBERTSONS : return "ALBERTSONS"
        case .AMAZON : return "AMAZON"
//        case .AMAZON_BETA : return "AMAZON_BETA"
        case .AMAZON_CA : return "AMAZON_CA"
        case .AMAZON_UK : return "AMAZON_UK"
        case .BED_BATH_AND_BEYOND : return "BED_BATH_AND_BEYOND"
        case .BESTBUY : return "BESTBUY"
        case .BJS_WHOLESALE : return "BJS_WHOLESALE"
        case .CHEWY : return "CHEWY"
        case .COSTCO : return "COSTCO"
        case .CVS : return "CVS"
        case .DICKS_SPORTING_GOODS : return "DICKS_SPORTING_GOODS"
        case .DOLLAR_GENERAL : return "DOLLAR_GENERAL"
        case .DOLLAR_TREE : return "DOLLAR_TREE"
        case .DOMINOS_PIZZA : return "DOMINOS_PIZZA"
        case .DOOR_DASH : return "DOOR_DASH"
        case .DRIZLY : return "DRIZLY"
        case .FAMILY_DOLLAR : return "FAMILY_DOLLAR"
        case .FOOD_4_LESS : return "FOOD_4_LESS"
        case .FOOD_LION : return "FOOD_LION"
        case .FRED_MEYER : return "FRED_MEYER"
        case .GAP : return "GAP"
        case .GIANT_EAGLE : return "GIANT_EAGLE"
        case .GMAIL : return nil
        case .GRUBHUB : return "GRUBHUB"
        case .HARRIS_TEETER : return "HARRIS_TEETER"
        case .HEB : return "HEB"
        case .HOME_DEPOT : return "HOME_DEPOT"
        case .HYVEE : return "HYVEE"
        case .INSTACART : return "INSTACART"
        case .JEWEL_OSCO : return "JEWEL_OSCO"
        case .KOHLS : return "KOHLS"
        case .KROGER : return "KROGER"
        case .LOWES : return "LOWES"
        case .MACYS : return "MACYS"
        case .MARSHALLS : return "MARSHALLS"
        case .MEIJER : return "MEIJER"
        case .NIKE : return "NIKE"
        case .PUBLIX : return "PUBLIX"
        case .RALPHS : return "RALPHS"
        case .RITE_AID : return  "RITE_AID"
        case .SAFEWAY : return "SAFEWAY"
        case .SAMS_CLUB : return "SAMS_CLUB"
        case .SEAMLESS : return "SEAMLESS"
        case .SEPHORA : return "SEPHORA"
        case .SHIPT : return "SHIPT"
        case .SHOPRITE : return "SHOPRITE"
        case .SPROUTS : return "SPROUTS"
        case .STAPLES : return "STAPLES"
        case .STARBUCKS : return "STARBUCKS"
        case .TACO_BELL : return "TACO_BELL"
        case .TARGET : return "TARGET"
        case .TJ_MAXX : return "TJ_MAXX"
        case .UBER_EATS : return "UBER_EATS"
        case .ULTA : return "ULTA"
        case .VONS : return "VONS"
        case .WALGREENS : return "WALGREENS"
        case .WALMART : return "WALMART"
        case .WALMART_CA : return "WALMART_CA"
        case .WEGMANS : return "WEGMANS"
        case .POSTMATES: return "POSTMATES"
        }
    }
}
