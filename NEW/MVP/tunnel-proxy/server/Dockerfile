FROM python:3.11-slim

WORKDIR /app

RUN pip install --no-cache-dir aiohttp

COPY tunnel_server.py .

ENV WS_PORT=8765
ENV TUNNEL_SECRET=change_me
ENV API_KEY=change_me
ENV REQUEST_TIMEOUT=60

EXPOSE 8765

CMD ["python", "tunnel_server.py"]
