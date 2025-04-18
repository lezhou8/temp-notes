document.addEventListener("DOMContentLoaded", () => {
	const uuid = getCookieValue("uuid")
	if (uuid) {
		window.location.href = `note.html?uuid=${encodeURIComponent(uuid)}`;
		return;
	}

	let cookiesAccepted = false;
	const acceptBtn = document.getElementById("accept-cookies-btn");
	const rejectBtn = document.getElementById("reject-cookies-btn");
	const resultSpan = document.getElementById("cookie-consent-result");

	acceptBtn.addEventListener("click", () => {
		cookiesAccepted = true;
		resultSpan.textContent = "(accepted)"
	});

	rejectBtn.addEventListener("click", () => {
		cookiesAccepted = false;
		resultSpan.textContent = "(rejected)"
	});

	const button = document.getElementById("create-btn");
	if (!button) return;

	button.addEventListener("click", () => {
		fetch("/api/note", {
			method: "POST",
			headers: { "content-type": "application/json" },
		})
			.then(response => {
				if (!response.ok) throw new Error("Failed to create note");
				return response.json();
			})
			.then(data => {
				if (cookiesAccepted)
					document.cookie = `uuid=${data.uuid}; path=/; expires=${new Date(data.expiry).toUTCString()}`;
				window.location.href = `note.html?uuid=${encodeURIComponent(data.uuid)}`;
			})
			.catch(error => { console.error(error); });
	});
});

function getCookieValue(name) {
	const value = `; ${document.cookie}`;
	const parts = value.split(`; ${name}=`);
	if (parts.length === 2) return parts.pop().split(';').shift();
	return null;
}
