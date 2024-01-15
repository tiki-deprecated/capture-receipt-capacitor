import * as fsPromises from 'fs/promises';
import * as path from 'path';
import * as process from 'process';
import { authenticate } from '@google-cloud/local-auth';
import { google } from 'googleapis';


// If modifying these scopes, delete token.json.
const SCOPES: string[] = ['https://www.googleapis.com/auth/gmail.readonly'];
// The file token.json stores the user's access and refresh tokens, and is
// created automatically when the authorization flow completes for the first
// time.
const TOKEN_PATH: string = path.join(process.cwd(), 'token.json');
const CREDENTIALS_PATH: string = path.join(process.cwd(), 'credentials.json');

export class GmailService {
  /**
   * Reads previously authorized credentials from the save file.
   *
   * @return {Promise<OAuth2Client|null>}
   */
  private async loadSavedCredentialsIfExist(){
    try {
      const content = await fsPromises.readFile(TOKEN_PATH, 'utf-8');
      const credentials = JSON.parse(content);
      return google.auth.fromJSON(credentials);
    } catch (err) {
      return null;
    }
  }

  /**
 * Serializes credentials to a file compatible with GoogleAuth.fromJSON.
 *
 * @param {OAuth2Client} client
 * @return {Promise<void>}
 */
private async saveCredentials(client: any): Promise<void> {
    const content = await fsPromises.readFile(CREDENTIALS_PATH, 'utf-8');
    const keys = JSON.parse(content);
    const key = keys.installed || keys.web;
    const payload = JSON.stringify({
      type: 'authorized_user',
      client_id: key.client_id,
      client_secret: key.client_secret,
      refresh_token: client.credentials?.refresh_token,
    });
    await fsPromises.writeFile(TOKEN_PATH, payload, 'utf-8');
  }

  /**
 * Load or request or authorization to call APIs.
 *
 */
async authorize() {
    let client = await this.loadSavedCredentialsIfExist();
    if (client) {
      return client;
    }
    let newClient = await authenticate({
      scopes: SCOPES,
      keyfilePath: CREDENTIALS_PATH,
    });
    if (newClient.credentials) {
      await this.saveCredentials(newClient);
    }
    return newClient;
  }
}
