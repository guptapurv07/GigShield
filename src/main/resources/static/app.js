// State variables
let currentWorkerId = null;

// 1. Register Worker Logic
document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const btn = e.target.querySelector('button');
    btn.textContent = "Registering...";

    const payload = {
        fullName: document.getElementById('fullName').value,
        email: `worker${Date.now()}@example.com`,
        password: "SecurePass!123", 
        phone: Math.floor(1000000000 + Math.random() * 9000000000).toString(),
        aadhaarNumber: Math.floor(100000000000 + Math.random() * 900000000000).toString(),
        platform: "Zomato",
        city: document.getElementById('city').value,
        vehicleType: "BIKE",
        averageWeeklyEarnings: parseFloat(document.getElementById('weeklyEarnings').value)
    };

    try {
        const res = await fetch('/api/v1/workers/register', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
        
        const data = await res.json();
        const resBox = document.getElementById('registerResponse');
        
        if (res.ok) {
            currentWorkerId = data.workerId;
            resBox.innerHTML = `<strong>✅ Success!</strong> Worker ID: ${data.workerId} | Base Premium: ₹${data.estimatedWeeklyPremium}`;
            resBox.className = "response-box success";
            
            // Enable next step
            document.getElementById('buyPolicyBtn').disabled = false;
        } else {
            resBox.innerHTML = `<strong>❌ Error:</strong> ${JSON.stringify(data.errors || data)}`;
            resBox.className = "response-box error";
        }
    } catch (err) {
        console.error(err);
    } finally {
        btn.textContent = "Register Worker";
    }
});

// 2. Buy Policy Logic (AI Pricing)
document.getElementById('buyPolicyBtn').addEventListener('click', async (e) => {
    if (!currentWorkerId) return;
    
    const btn = e.target;
    btn.textContent = "Calculating AI Premium...";
    const zoneOpt = document.getElementById('hyperLocalZone');
    const zoneValue = zoneOpt.value;

    try {
        const res = await fetch(`/api/v1/policy/purchase/${currentWorkerId}?hyperLocalZone=${zoneValue}`, {
            method: 'POST'
        });
        
        const data = await res.json();
        const resBox = document.getElementById('policyResponse');
        
        if (res.ok) {
            resBox.innerHTML = `<strong>🛡️ Policy Active!</strong><br>
                                Paid: ₹${data.premiumPaid} <em>(AI Engine Discounted)</em><br> 
                                Coverage Limits: ₹${data.maxPayoutCoverage}`;
            resBox.className = "response-box success drop-in";
        } else {
            resBox.innerHTML = `<strong>❌ Error:</strong> ${JSON.stringify(data)}`;
            resBox.className = "response-box error";
        }
    } catch (err) {
        console.error(err);
    } finally {
        btn.textContent = "Generate Dynamic Policy";
    }
});

// 3. Trigger Disasters (Zero-Touch Payouts)
document.getElementById('triggerDisastersBtn').addEventListener('click', async (e) => {
    const btn = e.target;
    btn.textContent = "Processing Worldwide Sensors...";
    
    const typeValue = document.getElementById('disruptionType').value;
    const severityVal = parseInt(document.getElementById('severityLevel').value);

    const payload = {
        type: typeValue,
        city: "Mumbai",
        severity: severityVal
    };

    try {
        const res = await fetch('/api/v1/webhooks/disruption', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload)
        });
        
        const data = await res.json();
        const resBox = document.getElementById('disruptionResponse');
        
        if (res.ok) {
            resBox.innerHTML = `🚨 <strong>ZERO-TOUCH PAYOUT TRIGGERED!</strong><br>
                                ${data.message}`;
            resBox.className = "response-box success pulse";
        } else {
            resBox.innerHTML = `<strong>❌ Error:</strong> ${JSON.stringify(data)}`;
            resBox.className = "response-box error";
        }
    } catch (err) {
        console.error(err);
    } finally {
        btn.textContent = "⚡ Broadcast System Disruption Event";
    }
});
