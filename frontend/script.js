document.addEventListener("DOMContentLoaded", () => {
    const contactForm = document.getElementById("contactForm");
    const contactList = document.getElementById("contactList");
    let contacts = [];

    contactForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const fullName = document.getElementById("fullName").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        const email = document.getElementById("email").value;
        const idNumber = document.getElementById("idNumber").value;
        const dob = document.getElementById("dob").value;
        const gender = document.getElementById("gender").value;
        const organization = document.getElementById("organization").value;

        const maskedName = maskName(fullName);
        const maskedPhone = maskPhone(phoneNumber);
        const hashedPhone = await hashPhoneNumber(phoneNumber);

        const contact = {
            fullName,
            phoneNumber,
            email,
            idNumber,
            dob,
            gender,
            organization,
            maskedName,
            maskedPhone,
            hashedPhone
        };

        contacts.push(contact);
        updateTable();
        contactForm.reset();
    });

    function maskName(name) {
        return name.split(" ")[0] + " ***";
    }

    function maskPhone(phone) {
        return phone.substring(0, 6) + "***" + phone.slice(-3);
    }

    async function hashPhoneNumber(phone) {
        const encoder = new TextEncoder();
        const data = encoder.encode(phone);
        const hashBuffer = await crypto.subtle.digest("SHA-256", data);
        return Array.from(new Uint8Array(hashBuffer)).map(b => b.toString(16).padStart(2, '0')).join('');
    }

    function updateTable() {
        contactList.innerHTML = "";
        contacts.forEach((contact, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${contact.maskedName}</td>
                <td>${contact.maskedPhone}</td>
                <td>${contact.hashedPhone}</td>
                <td><button onclick="deleteContact(${index})">Delete</button></td>
            `;
            contactList.appendChild(row);
        });
    }

    window.deleteContact = (index) => {
        contacts.splice(index, 1);
        updateTable();
    };
});
