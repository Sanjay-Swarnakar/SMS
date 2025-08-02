<!-- Footer -->
<footer style="background: var(--header-bg); color: var(--header-text); text-align: center; padding: 10px 0; margin-top: 20px;">
    <p>&copy; <%= java.time.Year.now() %> Student Management System. All rights reserved.</p>
</footer>

<!-- Dark Mode + Sidebar Toggle Scripts -->
<div id="overlay" onclick="toggleSidebar()" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:#00000077;z-index:999;"></div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        let savedTheme = localStorage.getItem('theme');
        if (!savedTheme) {
            savedTheme = window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
            localStorage.setItem('theme', savedTheme);
        }
        if (savedTheme === 'dark') document.body.classList.add('dark-mode');
    });

    function toggleDarkMode() {
        const isDark = document.body.classList.toggle('dark-mode');
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    }

    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        const overlay = document.getElementById('overlay');
        const isCollapsed = sidebar.classList.toggle('collapsed');
        if (window.innerWidth <= 768) {
            overlay.style.display = isCollapsed ? 'none' : 'block';
        }
    }
</script>
