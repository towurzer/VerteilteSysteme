const http = require("http");
const fs = require("fs");
const nodemailer = require("nodemailer");

class EmailClient {
  constructor(username, password) {
    this.username = username;
    this.transporter = nodemailer.createTransport({
      host: "smtp.ethereal.email",
      port: 587,
      auth: {
        user: username,
        pass: password,
      },
    });
  }

  async sendMail({ recipient, subject, message }) {
    const payload = {
      from: this.username,
      to: recipient,
      subject,
      text: `${message}`,
    };

    try {
      const info = await this.transporter.sendMail(payload);
      console.log("Message sent: %s", info.messageId);
    } catch (err) {
      throw new Error("Sending mail failed");
    }
  }
}

const hostname = "127.0.0.1";
const port = 3000;
const username = `arvel62@ethereal.email`;
const password = `Fz84XayAkcdhkyCNHm`;
const emailClient = new EmailClient(username, password);

const server = http.createServer((req, res) => {
  if (req.url.endsWith("/sendEmail") && req.method === "POST") {
    let body = "";
    req.on("data", function (chunk) {
      body += chunk;
    });

    req.on("end", function () {
      console.log(`Received request: ${body}`);
      try {
        emailClient.sendMail(JSON.parse(body));
        res.writeHead(200);
      } catch (err) {
        console.error("Error when sending mail");
        res.writeHead(400);
      }
      res.end();
    });
  } else {
    //Display index.html File
    fs.readFile("./index.html", (err, file) => {
      res.writeHead(200, {
        "content-type": "text/html",
        "Content-Length": file.length,
      });
      res.end(file);
    });
  }
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
