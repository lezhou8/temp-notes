document.addEventListener("DOMContentLoaded", () => {
	const params = new URLSearchParams(window.location.search);
	const uuid = params.get("uuid");
	if (!uuid) {
		console.error("No uuid parameter in url");
		return;
	}

	fetch(`/api/note/${encodeURIComponent(uuid)}`)
		.then(response => {
			if (!response.ok) throw new Error("Failed to fetch note");
			return response.json();
		})
		.then(data => {
			const textarea = document.getElementById("content-area");
			if (textarea) textarea.value = data.content || "";
			const expirySpan = document.getElementById("expiry-text");
			if (expirySpan) expirySpan.textContent = new Date(data.expiry).toLocaleString();
			document.cookie = `uuid=${data.uuid}; path=/; expires=${new Date(data.expiry).toUTCString()}`;
		})
		.catch(error => {
			console.error(error);
		});

	const button = document.getElementById("save-btn");
	if (!button) return;

	button.addEventListener("click", () => {
		const content = document.getElementById("content-area")?.value ?? "";

		const MAX_LENGTH = 5000;
		if (content.length > MAX_LENGTH) {
			alert(`Note is too long. Maximum length is ${MAX_LENGTH} characters.`);
			return;
		}

		fetch(`/api/note/${encodeURIComponent(uuid)}`, {
			method: "PATCH",
			headers: { "content-type": "application/json" },
			body: JSON.stringify({ content })
		})
			.then(response => {
				if (!response.ok) throw new Error("Failed to save note");
				alert("Note saved successfully");
			})
			.catch(error => {
				console.error(error);
				alert("Error while saving note");
			});
	});
});
