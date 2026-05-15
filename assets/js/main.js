document.addEventListener('DOMContentLoaded', () => {
    // Mermaid Initialization
    if (typeof mermaid !== 'undefined') {
        mermaid.initialize({
            startOnLoad: true,
            theme: 'dark',
            fontFamily: 'Inter, sans-serif',
            flowchart: { nodeSpacing: 50, rankSpacing: 60, curve: 'basis' },
            themeVariables: { fontSize: '15px' }
        });
    }

    // Scroll reveal
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(e => {
            if (e.isIntersecting) {
                e.target.classList.add('visible');
                observer.unobserve(e.target);
            }
        });
    }, observerOptions);

    document.querySelectorAll('.feature, .layer, .step, .stack-item, .repo-stats').forEach(el => observer.observe(el));

    // Nav Scroll Effect
    const nav = document.querySelector('.nav');
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            nav.classList.add('nav--scrolled');
        } else {
            nav.classList.remove('nav--scrolled');
        }
    });

    // Gallery tabs + thumbnails
    window.switchPanel = function(panelId) {
        document.querySelectorAll('.gallery-panel').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.gallery-tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.gallery-thumb').forEach(t => t.classList.remove('active'));
        
        const targetPanel = document.getElementById(panelId);
        if (targetPanel) targetPanel.classList.add('active');
        
        const targetTab = document.querySelector(`.gallery-tab[data-panel="${panelId}"]`);
        if (targetTab) targetTab.classList.add('active');
        
        const targetThumb = document.querySelector(`.gallery-thumb[data-panel="${panelId}"]`);
        if (targetThumb) targetThumb.classList.add('active');
    };

    document.querySelectorAll('.gallery-tab').forEach(tab => {
        tab.addEventListener('click', () => switchPanel(tab.dataset.panel));
    });
    document.querySelectorAll('.gallery-thumb').forEach(thumb => {
        thumb.addEventListener('click', () => switchPanel(thumb.dataset.panel));
    });

    // Mobile menu toggle
    const hamburger = document.querySelector('.nav__hamburger');
    const navLinks = document.querySelectorAll('.nav__links a');

    if (hamburger) {
        hamburger.addEventListener('click', () => {
            nav.classList.toggle('nav--open');
            document.body.style.overflow = nav.classList.contains('nav--open') ? 'hidden' : '';
        });
    }

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            nav.classList.remove('nav--open');
            document.body.style.overflow = '';
        });
    });

    // Smooth scroll
    document.querySelectorAll('a[href^="#"]').forEach(a => {
        a.addEventListener('click', function (e) {
            e.preventDefault();
            const href = this.getAttribute('href');
            if (href === '#') return;
            const target = document.querySelector(href);
            if (target) {
                const navHeight = 80;
                window.scrollTo({ 
                    top: target.getBoundingClientRect().top + window.pageYOffset - navHeight, 
                    behavior: 'smooth' 
                });
            }
        });
    });

    // --- GitHub API Dynamic Integration ---
    const GITHUB_REPO = 'SBAI-Youness/ReConan';
    const API_BASE = `https://api.github.com/repos/${GITHUB_REPO}`;

    async function fetchGitHubData() {
        try {
            // Fetch Releases
            const releasesRes = await fetch(`${API_BASE}/releases`);
            const releases = await releasesRes.json();
            
            if (releases && releases.length > 0) {
                const latest = releases[0]; // Most recent release (including prerelease)
                updateReleaseInfo(latest);
            }

            // Fetch Repo Stats
            const repoRes = await fetch(API_BASE);
            const repoData = await repoRes.json();
            updateRepoStats(repoData);

        } catch (error) {
            console.error('Error fetching GitHub data:', error);
        }
    }

    function updateReleaseInfo(release) {
        const badge = document.querySelector('.hero .badge');
        
        if (badge) {
            badge.href = release.html_url;
            badge.innerHTML = `<span class="badge__dot"></span> Latest Release: ${release.tag_name}`;
            badge.style.cursor = 'pointer';
        }
    }

    function updateRepoStats(data) {
        const statsContainer = document.querySelector('.repo-stats');
        if (statsContainer) {
            statsContainer.innerHTML = `
                <a href="${data.html_url}/stargazers" target="_blank" class="stat-item">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor"><path d="M12 2l3.09 6.26L22 9.27l-5 4.87L18.18 22 12 18.27 5.82 22 7 14.14l-5-4.87 6.91-1.01L12 2z"/></svg>
                    <span>${data.stargazers_count} Stars</span>
                </a>
                <a href="${data.html_url}/network/members" target="_blank" class="stat-item">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="18" cy="5" r="3"/><circle cx="6" cy="12" r="3"/><circle cx="18" cy="19" r="3"/><line x1="8.59" y1="13.51" x2="15.42" y2="17.49"/><line x1="15.41" y1="6.51" x2="8.59" y2="10.49"/></svg>
                    <span>${data.forks_count} Forks</span>
                </a>
            `;
            statsContainer.classList.add('visible');
        }
    }

    fetchGitHubData();
});
