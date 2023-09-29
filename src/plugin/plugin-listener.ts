import type { PluginResponse } from './plugin-response'

export type PluginListener = (rsp: PluginResponse) => void
