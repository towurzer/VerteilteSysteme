document.getElementById("emailForm").addEventListener("submit", async (e) => {
  // Execute that function asynchronous when the "emailForm" is submitted
  e.preventDefault(); // No reloading the site and deleting form
  const statusDiv = document.getElementById("status"); // fetch element
  const formData = new FormData(e.target); // create formData to extract values
  const delay = formData.get("delay") ?? 0; // extract values

  statusDiv.className = "status loading";
  statusDiv.innerHTML = `Scheduling email... (Will send in ${delay} seconds)`;

  try {
    const response = await fetch("/send", {
      method: "POST",
      body: new URLSearchParams(formData),
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message);
    }

    if (data.success && data.previewUrl) {
      statusDiv.className = "status success";
      statusDiv.textContent = `Redirecting to preview...`;
      window.location.href = data.previewUrl;
    } else if (data.success) {
      statusDiv.className = "status success";
      statusDiv.textContent = `Email sent successfully!`;
    } else {
      throw new Error(data.message);
    }
  } catch (error) {
    console.error("error:", error);
    statusDiv.className = "status error";
    statusDiv.textContent = `Error: ${error.message}`;
  }
});
