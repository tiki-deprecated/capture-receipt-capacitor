export class ReceiptService {

  async upload(files: File[]) {
    const formData = new FormData();

    files.forEach((file) => {
      formData.append('files', file);
    });

    try {
      const response = await fetch('https://postman-echo.com/post', {
        method: 'POST',
        body: formData,
      });

      if (response.ok) {
        const responseData = await response.json();
        console.log('Upload successful. Response:', responseData);
      } else {
        console.error('Error uploading files. Status:', response.status);
      }
    } catch (error) {
      console.error('Error uploading files:');
    }
  }
}
