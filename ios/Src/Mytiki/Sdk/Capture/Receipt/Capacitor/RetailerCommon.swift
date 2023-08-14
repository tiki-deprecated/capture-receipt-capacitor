/*
* Copyright (c) TIKI Inc.
* MIT license. See LICENSE file in root directory.
*/

import Foundation
import BlinkEReceipt
import Capacitor

enum RetailerCommon: String {
    
    case amazon
    case amazon_beta
    case amazon_ca_beta
    case amazon_uk_beta
    case cvs
    case heb
    case acme_markets
    case albertsons
    case bed_bath_and_beyond
    case bestbuy
    case bjs_wholesale
    case chewy
    case costco
    case dicks_sporting_goods
    case dollar_general
    case dollar_tree
    case dominos_pizza
    case door_dash
    case drizly
    case family_dollar
    case food_4_less
    case food_lion
    case fred_meyer
    case gap
    case giant_eagle
    case grubhub
    case harris_teeter
    case home_depot
    case hyvee
    case instacart
    case jewel_osco
    case kohls
    case kroger
    case lowes
    case macys
    case marshalls
    case meijer
    case nike
    case publix
    case ralphs
    case rite_aid
    case safeway
    case sams_club
    case seamless
    case sephora
    case shipt
    case shoprite
    case sprouts
    case staples
    case starbucks
    case taco_bell
    case target
    case tj_maxx
    case uber_eats
    case ulta
    case vons
    case walgreens
    case walmart
    case walmart_ca
    case wegmans
    
    
    func toBRAccountLinkingRetailer() -> BRAccountLinkingRetailer? {
        switch self{
            case .amazon : return .amazon
            case .amazon_beta : return .amazonBeta
            case .amazon_ca_beta : return .amazonBetaCA
            case .amazon_uk_beta : return .amazonBetaUK
            case .cvs : return .CVS
            case .heb : return .HEB
            case .acme_markets : return .acmeMarkets
            case .albertsons : return .albertsons
            case .bed_bath_and_beyond : return .bedBath
            case .bestbuy : return .bestBuy
            case .bjs_wholesale : return .bjs
            case .chewy : return .chewy
            case .costco : return .costco
            case .dicks_sporting_goods : return .dicksSportingGoods
            case .dollar_general : return .dollarGeneral
            case .dollar_tree : return .dollarTree
            case .dominos_pizza : return .dominosPizza
            case .door_dash : return .doordash
            case .drizly : return .drizly
            case .family_dollar : return .familyDollar
            case .food_4_less : return .food4Less
            case .food_lion : return .foodLion
            case .fred_meyer : return .fredMeyer
            case .gap : return .gap
            case .giant_eagle : return .giantEagle
            case .grubhub : return .grubhub
            case .harris_teeter : return .harrisTeeter
            case .home_depot : return .homeDepot
            case .hyvee : return .hyVee
            case .instacart : return .instacart
            case .jewel_osco : return .jewelOsco
            case .kohls : return .kohls
            case .kroger : return .kroger
            case .lowes : return .lowes
            case .macys : return .macys
            case .marshalls : return .marshalls
            case .meijer : return .meijer
            case .nike : return .nike
            case .publix : return .publix
            case .ralphs : return .ralphs
            case .rite_aid : return .riteAid
            case .safeway : return .safeway
            case .sams_club : return .samsClub
            case .seamless : return .seamless
            case .sephora : return .sephora
            case .shipt : return .shipt
            case .shoprite : return .shoprite
            case .sprouts : return .sprouts
            case .staples : return .staples
            case .starbucks : return .starbucks
            case .taco_bell : return .tacoBell
            case .target : return .target
            case .tj_maxx : return .tjMaxx
            case .uber_eats : return .uberEats
            case .ulta : return .ulta
            case .vons : return .vons
            case .walgreens : return .walgreens
            case .walmart : return .walmart
            case .walmart_ca : return .walmartCA
            case .wegmans : return .wegmans
        default : return nil
        }
    }
}
