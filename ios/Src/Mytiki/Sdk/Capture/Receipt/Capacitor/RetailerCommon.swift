/*
* Copyright (c) TIKI Inc.
* MIT license. See LICENSE file in root directory.
*/

import Foundation
import BlinkEReceipt
import Capacitor

public enum RetailerCommon: String {
    
    case ACME_MARKETS
    case ALBERTSONS
    case AMAZON
    case AMAZON_BETA
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
//    case STAPLES_CA
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
    
    
    func toBRAccountLinkingRetailer() -> BRAccountLinkingRetailer? {
        switch self{
            case .ACME_MARKETS : return .acmeMarkets
            case .ALBERTSONS : return .albertsons
            case .AMAZON : return .amazon
            case .AMAZON_BETA : return .amazonBeta
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
//   TODO         case .STAPLES_CA : return STAMPLESCA
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

        }
    }
}
