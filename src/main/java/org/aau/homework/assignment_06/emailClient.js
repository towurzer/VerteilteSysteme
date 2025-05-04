const express = require("express");
const bodyParser = require("body-parser");
const nodemailer = require("nodemailer");

const app = express();
const PORT = 8080;
const host = "smtp.ethereal.email";
const port = 587;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static("."));

app.get("/", (req, res) => {
  res.sendFile(__dirname + "/index.html");
});
const wait = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

app.post("/send", async (req, res) => {
  const { email, password, to, subject, body, delay } = req.body;
  const delayMs = parseInt(delay) * 1000;

  console.log(`Scheduling email to ${to} in ${delay} seconds...`);

  try {
    await wait(delayMs);

    console.log(`Sending email to ${to}...`);

    const transporter = nodemailer.createTransport({
      host: host,
      port: port,
      secure: port === 465,
      auth: {
        user: email,
        pass: password,
      },
    });

    const sendMailPromise = new Promise((resolve, reject) => {
      const mailOptions = {
        from: `"Scheduled Sender" <${email}>`,
        to,
        subject,
        text: body,
      };

      transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
          console.error("Failed to send email:", error);
          reject(error); // Reject the promise on error
        } else {
          console.log("Email sent:", info.messageId);
          const previewUrl = nodemailer.getTestMessageUrl(info);
          console.log("Preview URL:", previewUrl);
          resolve({ previewUrl: previewUrl });
        }
      });
    });

    const result = await sendMailPromise;

    res.json({
      success: true,
      message: `Email sent successfully after ${delay} seconds!`,
      previewUrl: result.previewUrl,
    });
  } catch (error) {
    console.error("Error during email sending:", error);
    res.status(500).json({
      success: false,
      message: "Failed to send email.",
    });
  }
});

app.listen(PORT, () => {
  console.log(`Server is running at http://localhost:${PORT}`);
});
