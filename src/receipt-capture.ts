/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account, AccountProvider } from './account';
import { providers } from './account';
import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin } from './receipt-capture-plugin';

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
     * Initializes the receipt capture plugin with a license key and optional product intelligence key.
     * @param licenseKey - The license key for your application.
     * @param productKey - The optional product intelligence key for your application.
     * @throws Error if the initialization fails.
     */
    initialize = async (licenseKey: string, productKey?: string): Promise<void> => {
        const rsp = await this.plugin.initialize({
            licenseKey: licenseKey,
            productKey: productKey,
        });
        if (!rsp.isInitialized) {
            throw Error(rsp.reason);
        }
    };

    /**
     * Initiates the receipt scan UI and returns the scanned receipt.
     * @returns A Promise that resolves to the scanned Receipt object.
     */
    scan = (): Promise<Receipt> => this.plugin.scan();

    /**
     * Logs in to an email account using IMAP.
     * @param username - The account username.
     * @param password - The account password.
     * @param provider - The {@link AccountProvider}.
     * @returns A Promise that resolves to the logged-in Account object.
     */
    loginWithEmail = async (username: string, password: string, provider: AccountProvider): Promise<Account> => {
        const rsp = await this.plugin.loginWithEmail({
            username: username,
            password: password,
            provider: provider.valueOf(),
        });
        return {
            username: rsp.username,
            provider: providers.get(rsp.provider),
        };
    };

    /**
     * Scrapes all logged-in email inboxes for receipts, calling the provided callback
     * function when each inbox scrape completes.
     * @param callback - A callback function to receive the scrape receipts.
     * @returns A Promise that resolves when scraping is complete.
     */
    scrapeEmail = (callback: (account: Account, receipts: Receipt[]) => void): Promise<void> =>
        this.plugin.scrapeEmail().then((rsp) => {
            callback(
                {
                    username: rsp.login.username,
                    provider: providers.get(rsp.login.provider),
                },
                rsp.scans,
            );
        });

    /**
     * Logs out and removes an email account from the local cache.
     * @param username - The account username.
     * @param password - The account password.
     * @param provider - The {@link AccountProvider}.
     * @returns A Promise that resolves when the account is removed.
     * @throws Error if removal fails.
     */
    removeEmail = async (username: string, password: string, provider: AccountProvider): Promise<void> => {
        const rsp = await this.plugin.removeEmail({
            username: username,
            password: password,
            provider: provider.valueOf(),
        });
        if (!rsp.success) throw Error(`Failed to remove: ${provider} | ${username}`);
    };

    /**
     * Load and verify all locally cached email accounts.
     * @returns A Promise that resolves to an array of Accounts.
     */
    verifyEmail = async (): Promise<Account[]> => {
        const rsp = await this.plugin.verifyEmail();
        return rsp.accounts.map((acc) => {
            return {
                username: acc.username,
                verified: acc.verified,
                provider: providers.get(acc.provider),
            };
        });
    };

    loginWithRetailer = async (
        username: string,
        password: string,
        provider: string,
    ): Promise<RetailerAccount> => {
        return this.plugin.loginWithRetailer({
            username,
            password,
            provider,
        })
    }

    retailers = async (): Promise<RetailerAccount[]> => {
        return (await this.plugin.retailers()).accounts
    }

    removeRetailer = async (
        username: string,
        provider: string,
    ): Promise<RetailerAccount> => {
        return await this.plugin.removeRetailer({ username, provider })
    }

    orders = async (): Promise<{
        provider: string,
        username: string,
        scan: Receipt;
    }> => {
        return await this.plugin.orders()
    }
}

interface RetailerAccount { username: string; provider: string, isVerified: boolean }

