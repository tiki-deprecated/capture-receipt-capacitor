/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from './account';
import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin, ReqInitialize, ScanType } from './receipt-capture-plugin';
import { LocalNotifications } from '@capacitor/local-notifications';

/**
 * The primary class for interacting with the Plugin.
 */
export class ReceiptCapture {
  private plugin: ReceiptCapturePlugin;

  /**
   * Constructs a new instance of the ReceiptCapture class. Do not construct
   * directly, use {@link instance}.
   * @param plugin - The ReceiptCapturePlugin instance used for communication.
   */
  constructor(plugin: ReceiptCapturePlugin) {
    this.plugin = plugin;
  }

  /**
   * Initializes the receipt capture plugin.
   * 
   * It initializes the Microblink SDK with the License Key and Product Intelligence Key.
   * To use Google OAuth for Gmail, provide a [Google OAuth Client Id]((https://developers.google.com/identity/protocols/oauth2) 
   * 
   * @param licenseKey - The license key for your application.
   * @param productKey - The product intelligence key for your application.
   * @param googleClientId - The Google Client Id for OAuth login. If not provided, Gmail scraping will
   * use IMAP login. 
   * 
   * @throws Error if the initialization fails.
   */
  initialize = async (licenseKey: string, productKey: string, googleClientId: string | undefined): Promise<void> => {
    await LocalNotifications.requestPermissions()
    const notifs = await LocalNotifications.schedule({
      notifications: [
        {
          title: 'Test Notification',
          body: 'Test Notification',
          id: 1,
          schedule: { at: new Date(Date.now() + 1000 * 5) },
          sound: undefined,
          attachments: undefined,
          actionTypeId: '',
          extra: null,
        },
      ],
    });
    console.log('scheduled notifications', notifs);
    
    const req: ReqInitialize = {
      licenseKey: licenseKey,
      productKey: productKey,
      googleClientId: googleClientId,
    };
    await this.plugin.initialize(req).catch((error) => {
      throw Error(error);
    });
  };

  /**
   * Login into a retailer or email account to scan for receipts.
   * 
   * @param username - the username of the account.
   * @param password - the password of the account
   * @param source - the source from that account, that can be an email service or a retailer service.
   * 
   * @returns - the Account interface with the logged in information.
   */
  login = (username: string, password: string, source: string): Promise<Account> =>
    this.plugin.login({ username, password, source });

  /**
   * Log out from one or all {@link Account}.
   * 
   * To logout from all accounts, do not provide username or password. 
   * 
   * @param username - the username of the account that will be logged out.
   * @param password - the password of the account that will be logged out
   * @param source - the source from that account, that can be an email service or a retailer service.
   * 
   * @returns - the Account that logged out
   */
  logout = (username?: string, password?: string, source?: string): Promise<Account> =>
    this.plugin.logout({ username: username!, password: password!, source: source! });

  /**
   * Scan for receipts. 
   * 
   * The scanType method defines which scan will be performed.
   * 
   * @param scanType - The type of the scan.
   * @param account - The account that will be scanned for receipts.
   * 
   * @returns - The scanned Receipt and a boolean indicates the execution.
   */
  scan = (
    scanType: ScanType | undefined,
    account?: Account,
  ): Promise<{ receipt: Receipt; isRunning: boolean; account?: Account }> => this.plugin.scan({ scanType, account });

  /**
   * Retrieves all saved email and accounts.
   * 
   * @returns - an array of Accounts.
   */
  accounts = async (): Promise<Account[]> => await this.plugin.accounts();
}
