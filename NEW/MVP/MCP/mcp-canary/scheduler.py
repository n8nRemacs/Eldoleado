"""Scheduler for API Canary periodic checks."""

import asyncio
import logging
from typing import Callable, Dict, List, Optional

from apscheduler.schedulers.asyncio import AsyncIOScheduler
from apscheduler.triggers.interval import IntervalTrigger

logger = logging.getLogger(__name__)


class CanaryScheduler:
    """Scheduler for running periodic health checks."""

    def __init__(self):
        self.scheduler = AsyncIOScheduler()
        self._jobs: Dict[str, str] = {}  # job_name -> job_id

    def add_health_check(
        self,
        name: str,
        func: Callable,
        interval_seconds: int,
        *args,
        **kwargs
    ):
        """Add a health check job."""
        job = self.scheduler.add_job(
            func,
            trigger=IntervalTrigger(seconds=interval_seconds),
            id=f"health_{name}",
            name=f"Health check: {name}",
            args=args,
            kwargs=kwargs,
            max_instances=1,
            coalesce=True,
            misfire_grace_time=30,
        )
        self._jobs[f"health_{name}"] = job.id
        logger.info(f"Added health check job: {name} (every {interval_seconds}s)")

    def add_api_check(
        self,
        name: str,
        func: Callable,
        interval_seconds: int,
        *args,
        **kwargs
    ):
        """Add an API check job."""
        job = self.scheduler.add_job(
            func,
            trigger=IntervalTrigger(seconds=interval_seconds),
            id=f"api_{name}",
            name=f"API check: {name}",
            args=args,
            kwargs=kwargs,
            max_instances=1,
            coalesce=True,
            misfire_grace_time=60,
        )
        self._jobs[f"api_{name}"] = job.id
        logger.info(f"Added API check job: {name} (every {interval_seconds}s)")

    def start(self):
        """Start the scheduler."""
        if not self.scheduler.running:
            self.scheduler.start()
            logger.info("Scheduler started")

    def stop(self):
        """Stop the scheduler."""
        if self.scheduler.running:
            self.scheduler.shutdown(wait=False)
            logger.info("Scheduler stopped")

    def get_jobs(self) -> List[Dict]:
        """Get list of scheduled jobs."""
        return [
            {
                "id": job.id,
                "name": job.name,
                "next_run": job.next_run_time.isoformat() if job.next_run_time else None,
            }
            for job in self.scheduler.get_jobs()
        ]

    async def run_now(self, job_name: str) -> bool:
        """Run a job immediately."""
        job_id = self._jobs.get(job_name)
        if not job_id:
            return False

        job = self.scheduler.get_job(job_id)
        if job:
            # Run the job's function
            try:
                if asyncio.iscoroutinefunction(job.func):
                    await job.func(*job.args, **job.kwargs)
                else:
                    job.func(*job.args, **job.kwargs)
                return True
            except Exception as e:
                logger.error(f"Error running job {job_name}: {e}")
                return False
        return False
