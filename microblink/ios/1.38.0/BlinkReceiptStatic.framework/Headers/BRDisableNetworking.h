//
//  BRDisableNetworking.h
//  BlinkReceipt
//
//  Created by Danny Panzer on 10/26/17.
//  Copyright © 2017 Windfall. All rights reserved.
//

#import "BRScanManager.h"
#import "BRScanOptions.h"

@interface BRScanManager ()

+ (void)disableNetworking;

@end

@interface BRScanOptions ()

@property (nonatomic) BOOL skipMerchantLookups;
@property (nonatomic) BOOL skipProductLookups;

@end
