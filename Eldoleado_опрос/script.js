// ===== Configuration =====
const CONFIG = {
    // URL для отправки данных (n8n webhook)
    WEBHOOK_URL: 'https://n8n.n8nsrv.ru/webhook/eldoleado-survey',
    // Включить режим разработки (сохранять в localStorage)
    DEV_MODE: false
};

// ===== Start Time Tracking =====
const startTime = Date.now();

// ===== DOM Elements =====
const form = document.getElementById('surveyForm');
const submitBtn = document.getElementById('submitBtn');
const successMessage = document.getElementById('successMessage');

// ===== Initialize =====
document.addEventListener('DOMContentLoaded', () => {
    initScaleInputs();
    initConditionalInputs();
    initFormValidation();
    initProgressTracking();
});

// ===== Scale Input Handlers =====
function initScaleInputs() {
    const scaleInputs = document.querySelectorAll('.scale-input');

    scaleInputs.forEach(input => {
        const valueDisplay = input.parentElement.querySelector('.scale-value');

        // Set initial value
        valueDisplay.textContent = input.value;

        // Update on change
        input.addEventListener('input', (e) => {
            valueDisplay.textContent = e.target.value;
        });
    });
}

// ===== Conditional Input Handlers =====
function initConditionalInputs() {
    // Q6: Other channel checkbox
    setupOtherInput('q6_other_check', 'q6_other');

    // Q7: Other main channel
    setupOtherRadio('q7', 'q7_other');

    // Q20: Other search method
    setupOtherRadio('q20', 'q20_other');

    // Q24: Other client sources
    setupOtherInput('q24_other_check', 'q24_other');

    // Q30: CAC amount
    const q30Yes = document.getElementById('q30_yes');
    const q30Amount = document.querySelector('input[name="q30_amount"]');
    if (q30Yes && q30Amount) {
        document.querySelectorAll('input[name="q30"]').forEach(radio => {
            radio.addEventListener('change', () => {
                q30Amount.disabled = !q30Yes.checked;
                if (q30Yes.checked) q30Amount.focus();
            });
        });
    }

    // Q33: CRM name
    const q33Crm = document.getElementById('q33_crm');
    const q33CrmName = document.querySelector('input[name="q33_crm_name"]');
    if (q33Crm && q33CrmName) {
        document.querySelectorAll('input[name="q33"]').forEach(radio => {
            radio.addEventListener('change', () => {
                q33CrmName.disabled = !q33Crm.checked;
                if (q33Crm.checked) q33CrmName.focus();
            });
        });
    }

    // Q45: Quit reason
    const q45Quit = document.getElementById('q45_quit');
    const q45Reason = document.querySelector('input[name="q45_quit_reason"]');
    if (q45Quit && q45Reason) {
        document.querySelectorAll('input[name="q45"]').forEach(radio => {
            radio.addEventListener('change', () => {
                q45Reason.disabled = !q45Quit.checked;
                if (q45Quit.checked) q45Reason.focus();
            });
        });
    }
}

function setupOtherInput(checkboxId, inputName) {
    const checkbox = document.getElementById(checkboxId);
    const input = document.querySelector(`input[name="${inputName}"]`);

    if (checkbox && input) {
        checkbox.addEventListener('change', () => {
            input.disabled = !checkbox.checked;
            if (checkbox.checked) input.focus();
        });
    }
}

function setupOtherRadio(radioName, inputName) {
    const radios = document.querySelectorAll(`input[name="${radioName}"]`);
    const input = document.querySelector(`input[name="${inputName}"]`);

    if (radios.length && input) {
        radios.forEach(radio => {
            radio.addEventListener('change', () => {
                const isOther = radio.value === 'other' && radio.checked;
                input.disabled = !isOther;
                if (isOther) input.focus();
            });
        });
    }
}

// ===== Form Validation =====
function initFormValidation() {
    form.addEventListener('submit', handleSubmit);
}

async function handleSubmit(e) {
    e.preventDefault();

    // Validate ranking uniqueness
    if (!validateRanking()) {
        alert('В вопросе 37 каждый ранг должен быть уникальным (от 1 до 6)');
        return;
    }

    // Collect form data
    const formData = collectFormData();

    // Calculate completion time
    formData.completion_time_seconds = Math.round((Date.now() - startTime) / 1000);

    // Show loading state
    submitBtn.disabled = true;
    submitBtn.querySelector('.btn-text').style.display = 'none';
    submitBtn.querySelector('.btn-loader').style.display = 'inline';

    try {
        if (CONFIG.DEV_MODE) {
            // Development mode - save to localStorage
            const responses = JSON.parse(localStorage.getItem('survey_responses') || '[]');
            responses.push({ ...formData, id: Date.now(), created_at: new Date().toISOString() });
            localStorage.setItem('survey_responses', JSON.stringify(responses));
            console.log('Survey saved to localStorage:', formData);
        } else {
            // Production mode - send to webhook
            const response = await fetch(CONFIG.WEBHOOK_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        }

        // Show success message
        form.style.display = 'none';
        successMessage.style.display = 'block';
        successMessage.scrollIntoView({ behavior: 'smooth' });

    } catch (error) {
        console.error('Error submitting survey:', error);
        alert('Произошла ошибка при отправке. Пожалуйста, попробуйте ещё раз.');

        // Reset button state
        submitBtn.disabled = false;
        submitBtn.querySelector('.btn-text').style.display = 'inline';
        submitBtn.querySelector('.btn-loader').style.display = 'none';
    }
}

function validateRanking() {
    const selects = document.querySelectorAll('.ranking-select');
    const values = [];

    for (const select of selects) {
        if (!select.value) return false;
        if (values.includes(select.value)) return false;
        values.push(select.value);
    }

    return values.length === 6;
}

// ===== Data Collection =====
function collectFormData() {
    const data = {};

    // Block 1: Workshop info
    data.q1_workshop_age = getRadioValue('q1');
    data.q2_employees = getRadioValue('q2');
    data.q3_clients_per_month = getRadioValue('q3');
    data.q4_average_check = getRadioValue('q4');
    data.q5_city = getInputValue('q5');

    // Block 2: Communication channels
    data.q6_channels = getCheckboxValues('q6');
    data.q6_channels_other = getInputValue('q6_other');
    data.q7_main_channel = getRadioValue('q7');
    data.q7_main_channel_other = getInputValue('q7_other');
    data.q8_channel_confusion = getRadioValue('q8');
    data.q9_confusion_problem_score = parseInt(getInputValue('q9')) || 5;

    // Block 3: After hours
    data.q10_after_hours_frequency = getRadioValue('q10');
    data.q11_after_hours_handling = getRadioValue('q11');
    data.q12_lost_clients_slow_response = getRadioValue('q12');
    data.q13_after_hours_problem_score = parseInt(getInputValue('q13')) || 5;

    // Block 4: Calls
    data.q14_calls_per_day = getRadioValue('q14');
    data.q15_call_logging = getRadioValue('q15');
    data.q16_forget_agreements = getRadioValue('q16');
    data.q17_record_calls = getRadioValue('q17');
    data.q18_calls_problem_score = parseInt(getInputValue('q18')) || 5;

    // Block 5: Suppliers
    data.q19_suppliers_count = getRadioValue('q19');
    data.q20_search_parts = getRadioValue('q20');
    data.q20_search_parts_other = getInputValue('q20_other');
    data.q21_time_to_compare = getRadioValue('q21');
    data.q22_price_mismatch = getRadioValue('q22');
    data.q23_suppliers_problem_score = parseInt(getInputValue('q23')) || 5;

    // Block 6: Marketing
    data.q24_client_sources = getCheckboxValues('q24');
    data.q24_client_sources_other = getInputValue('q24_other');
    data.q25_ad_budget = getRadioValue('q25');
    data.q26_has_marketer = getRadioValue('q26');
    data.q27_marketer_cost = getRadioValue('q27');
    data.q28_marketer_satisfaction = getRadioValue('q28');
    data.q29_track_conversion = getRadioValue('q29');
    data.q30_knows_cac = getRadioValue('q30') === 'yes';
    data.q30_cac_amount = parseInt(getInputValue('q30_amount')) || null;
    data.q31_marketing_problem_score = parseInt(getInputValue('q31')) || 5;

    // Block 7: Retention
    data.q32_repeat_clients = getRadioValue('q32');
    data.q33_client_database = getRadioValue('q33');
    data.q33_crm_name = getInputValue('q33_crm_name');
    data.q34_remind_clients = getRadioValue('q34');
    data.q35_reminder_method = getRadioValue('q35');
    data.q36_retention_problem_score = parseInt(getInputValue('q36')) || 5;

    // Block 8: Main pains
    data.q37_rank_messengers = parseInt(getSelectValue('q37_messengers')) || null;
    data.q37_rank_night_messages = parseInt(getSelectValue('q37_night')) || null;
    data.q37_rank_suppliers = parseInt(getSelectValue('q37_suppliers')) || null;
    data.q37_rank_phone_agreements = parseInt(getSelectValue('q37_phone')) || null;
    data.q37_rank_advertising = parseInt(getSelectValue('q37_advertising')) || null;
    data.q37_rank_retention = parseInt(getSelectValue('q37_retention')) || null;
    data.q38_magic_wand = getTextareaValue('q38');
    data.q39_time_waster = getTextareaValue('q39');

    // Block 9: Willingness to pay
    data.q40_current_software_spend = getRadioValue('q40');
    data.q41_willingness_to_pay = getRadioValue('q41');
    data.q42_important_function = getInputValue('q42_function');
    data.q42_function_price = parseInt(getInputValue('q42_price')) || null;
    data.q43_too_expensive = parseInt(getInputValue('q43')) || null;
    data.q44_suspiciously_cheap = parseInt(getInputValue('q44')) || null;

    // Block 10: Completion
    data.q45_tried_crm = getRadioValue('q45');
    data.q45_quit_reason = getInputValue('q45_quit_reason');
    data.q46_must_have_features = getTextareaValue('q46');
    data.q47_want_beta = getRadioValue('q47');
    data.q48_contact = getInputValue('q48');
    data.q49_referrals = getTextareaValue('q49');

    // Metadata
    data.user_agent = navigator.userAgent;

    return data;
}

// ===== Helper Functions =====
function getRadioValue(name) {
    const selected = document.querySelector(`input[name="${name}"]:checked`);
    return selected ? selected.value : null;
}

function getCheckboxValues(name) {
    const checked = document.querySelectorAll(`input[name="${name}"]:checked`);
    return Array.from(checked).map(cb => cb.value);
}

function getInputValue(name) {
    const input = document.querySelector(`input[name="${name}"]`);
    return input ? input.value.trim() : '';
}

function getTextareaValue(name) {
    const textarea = document.querySelector(`textarea[name="${name}"]`);
    return textarea ? textarea.value.trim() : '';
}

function getSelectValue(name) {
    const select = document.querySelector(`select[name="${name}"]`);
    return select ? select.value : '';
}

// ===== Progress Tracking =====
function initProgressTracking() {
    // Add progress bar to DOM
    const progressBar = document.createElement('div');
    progressBar.className = 'progress-bar';
    document.body.prepend(progressBar);

    // Update progress on scroll
    window.addEventListener('scroll', () => {
        const scrollTop = window.scrollY;
        const docHeight = document.documentElement.scrollHeight - window.innerHeight;
        const progress = (scrollTop / docHeight) * 100;
        progressBar.style.width = `${Math.min(progress, 100)}%`;
    });
}

// ===== Export for debugging =====
window.surveyDebug = {
    collectFormData,
    getStoredResponses: () => JSON.parse(localStorage.getItem('survey_responses') || '[]'),
    clearStoredResponses: () => localStorage.removeItem('survey_responses')
};
