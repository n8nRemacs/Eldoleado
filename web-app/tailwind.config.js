/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Channels
        'channel-whatsapp': '#25D366',
        'channel-telegram': '#0088cc',
        'channel-avito': '#00AAFF',
        'channel-vk': '#4C75A3',
        'channel-max': '#8B5CF6',
        // Messages
        'message-incoming': '#e0f2fe',
        'message-outgoing': '#fed7aa',
      },
    },
  },
  plugins: [],
}
