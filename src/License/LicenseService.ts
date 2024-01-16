/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as TikiSdkLicensing from '@mytiki/tiki-sdk-capacitor';
import { TitleRecord, CommonTags, Tag, Usecase, CommonUsecases, type LicenseRecord } from '@mytiki/tiki-sdk-capacitor';

export class LicenseService {
  private _userId?: string;
  private _providerId?: string;
  private _terms?: string;
  private _expiry?: Date;

  constructor() {}

  set userId(value: string | undefined) {
    this._userId = value;
  }

  set providerId(value: string | undefined) {
    this._providerId = value;
  }

  set terms(value: string | undefined) {
    this._terms = value;
  }

  set expiry(value: Date | undefined) {
    this._expiry = value;
  }

  /**
   * Initializes the SDK service for the specified user.
   * @param userId - The user's unique identifier to initialize the SDK for.
   * @param providerId - The company's unique identifier to initialize the SDK for.
   * @returns A Promise that resolves when the initialization is complete.
   */
  private async initialize(userId?: string, providerId?: string): Promise<void> {
    if ((!userId && !this._userId) || (!providerId && this._providerId))
      throw new Error('UserId/ProviderId is necessary to initialize');

    await TikiSdkLicensing.instance.initialize(userId! ?? this._userId, providerId! ?? this.providerId!);
  }

  /**
   * Creates a new license for the SDK service.
   * @param userId - The user's unique identifier to initialize the SDK for.
   * @param terms - The use terms for that license
   * @param expiry - The expiry date for that license
   * @returns A Promise that resolves when the license creation is complete.
   */
  async createLicense(userId?: string, terms?: string, expiry?: Date): Promise<LicenseRecord> {
    const isInitialized = await TikiSdkLicensing.instance.isInitialized();

    if (!isInitialized) this.initialize();

    const titleRecord: TitleRecord = await TikiSdkLicensing.instance
      .createTitle(userId! ?? this._userId, [Tag.common(CommonTags.PURCHASE_HISTORY)])
      .catch((error) => {
        console.log(error);
        throw new Error(error);
      });

    if (!titleRecord.id) throw new Error('Error to create a title!');

    return await TikiSdkLicensing.instance.createLicense(
      titleRecord.id,
      [
        {
          usecases: [Usecase.common(CommonUsecases.ATTRIBUTION)],
          destinations: ['*'],
        },
      ],
      terms! ?? this._terms,
      expiry! ?? this._expiry,
      'Receipt data',
    );
  }

  async verifyLicense(
    ptr: string,
    usecases: Usecase[],
    destinations?: string[],
  ): Promise<{
    success: boolean;
    reason?: string;
  }> {
    return await TikiSdkLicensing.instance.guard(ptr, usecases, destinations);
  }
}
