/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as TikiSdkLicensing from '@mytiki/tiki-sdk-capacitor';
import { TitleRecord, CommonTags, Tag, Usecase, CommonUsecases } from '@mytiki/tiki-sdk-capacitor';

export class LicenseService {
  private _userId?: string = 'default';
  private _providerId?: string = 'default';
  private _terms?: string = 'default';
  private _expiry?: Date;

  constructor() {}

  set userId(value: string | undefined) {
    this._userId = value || 'default';
  }

  set providerId(value: string | undefined) {
    this._providerId = value || 'default';
  }

  set terms(value: string | undefined) {
    this._terms = value || 'default';
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
  async initialize(userId?: string, providerId?: string): Promise<void> {
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
  async createLicense(userId?: string, terms?: string, expiry?: Date): Promise<void> {
    const isInitialized = await TikiSdkLicensing.instance.isInitialized()

    if(!isInitialized) this.initialize()

    const titleRecord: TitleRecord = await TikiSdkLicensing.instance.createTitle(userId! ?? this._userId, [
      Tag.common(CommonTags.PURCHASE_HISTORY),
    ]);
    await TikiSdkLicensing.instance.createLicense(
      titleRecord.id,
      [
        {
          usecases: [Usecase.common(CommonUsecases.ATTRIBUTION)],
          destinations: ['*'],
        },
      ],
      terms! ?? this._terms,
      expiry! ?? this._expiry,
      "Receipt data"
    );
  }

  async verifyLicense(ptr: string,  usecases: Usecase[], destinations?: string[]){
    return await TikiSdkLicensing.instance.guard(ptr, usecases, destinations);
  }
}
