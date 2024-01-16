import * as CryptoJS from 'crypto-js';

export class GmailAuth {
  private base64URLEncode(str: string): string {
    return btoa(str).replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
  }

  private sha256(plain: string): string {
    const words = CryptoJS.enc.Utf8.parse(plain);
    const hash = CryptoJS.SHA256(words);
    return hash.toString(CryptoJS.enc.Base64);
  }

  private generateCodeChallenge(codeVerifier: string): string {
    const hashedCodeVerifier = this.sha256(codeVerifier);
    const base64URLEncoded = this.base64URLEncode(hashedCodeVerifier);
    return base64URLEncoded;
  }

  private exchange(code: string) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Accept', 'application/json');

    const options = {
      method: 'POST',
      headers: headers,
      body: new URLSearchParams({
        client_id: 'client_id',
        client_secret: 'client_secret',
        redirect_uri: '',
        code: code,
        code_verifier: this.generateCodeChallenge('code_verifier'),
        grant_type: 'authorization_code',
      }),
    };
    const url = 'https://accounts.google.com/o/oauth2/v2/auth';
    fetch(url, options)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
      })
      .catch((error) => console.log(error));
  }

  async requestToken() {
    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Accept', 'application/json');

    const options = {
      method: 'POST',
      headers: headers,
      body: new URLSearchParams({
        client_id: 'client_id',
        redirect_uri: '',
        response_type: 'code',
        scope: 'email%20profile',
        code_challenge: this.generateCodeChallenge('code_verifier'),
        code_challenge_method: 'S256',
      }),
    };
    const url = 'https://accounts.google.com/o/oauth2/v2/auth';

    fetch(url, options)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
        this.exchange(response.code);
      })
      .catch((error) => console.log(error));
  }

  revoke(token: string) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Accept', 'application/json');

    const options = {
      method: 'POST',
      headers: headers,
      body: new URLSearchParams({
        token: token,
      }),
    };
    const url = 'https://oauth2.googleapis.com/revoke';

    fetch(url, options);
  }
}
