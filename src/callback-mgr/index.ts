/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { CallbackDetails } from './callback-details'
/**
 * The class responsible for handling the asynchronous callbacks between
 * the plugin and the Capacitor code.
 */
export class CallbackManager {

  /**
   * Adds a new callback
   * 
   * @param cbDetails Determines the details of the callback
   */
  add = (cbDetails: CallbackDetails) => {
    this.callbacks.set(cbDetails.id, cbDetails)
  }

  /**
   * Remove a callback
   * 
   * @param cbDetails Determines the details of the callback
   */
  remove = (cbDetails: CallbackDetails) => {
    this.callbacks.delete(cbDetails.id)
  }

  /**
   * Executes a callback
   * 
   * @param cbDetails 
   */
  fire = (cbDetails: CallbackDetails) => {
    const eventId = cbDetails.id
    const cb = this.callbacks.get(eventId)
    if(cb){
      const call = cb.callback
      const payload = cbDetails.payload
      if(call){
        call(payload)
      }
      this.remove(cbDetails)
    }
  }

  private callbacks = new Map<string, CallbackDetails>()
}