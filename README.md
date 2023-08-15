# TIKI Capture Receipt (Capacitor Plugin)

The TIKI Capture Receipt Plugin for Capacitor enables the extraction of receipt data from photos, email inboxes, and retailer accounts using [Microblink's](https://microblink.com) OCR technology.

This plugin wraps (and simplifies) Microblink's native [iOS](https://github.com/BlinkReceipt/blinkreceipt-ios) and [Android](https://github.com/BlinkReceipt/blinkreceipt-android) SDKs for use with Capacitor applications.

**Learn more at 🍍 [mytiki.com](https://mytiki.com), or jump right into the 📘 [API reference](https://tiki-capture-receipt-capacitor.mytiki.com).**


## Installing

Install the dependency from NPM

```
npm install @mytiki/tiki-capture-receipt-capacitor
npx cap sync
```

That's it. And yes, it's really that easy.

## Initialization

**IMPORTANT: Requires a valid Microblink BlinkReceipt License Key and Product Intelligence Key. [Reach out to get one →](https://mytiki.com)**


```ts
import { instance } from '@mytiki/tiki-capture-receipt-capacitor'

instance.initialize('<LICENSE KEY>', '<PRODUCT KEY>')
        .then((rsp) => console.log(`initialized`))
```

_NOTE: Only iOS and Android are supported._

# Contributing

- Use [GitHub Issues](https://github.com/tiki-bar/tiki-sdk-capacitor/issues) to report any bugs you find or to request enhancements.
- If you'd like to get in touch with our team or other active contributors, pop in our 👾 [Discord](https://discord.gg/tiki).
- Please use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) if you intend to add code to this project.

## Project Structure
- `/src`: The primary TS interface for the plugin.
- `/android`: The native Android implementation.
- `/ios`: The native iOS implementation.
- `/example`: A simple example project using the plugin

## Contributors ✨
