import { defineConfig } from "vitepress"

export default defineConfig({
  title: "state",
  description: "The State API documentation",
  themeConfig: {
    nav: [
      {
        text: "Javadoc",
        link: "https://javadoc.io/doc/me.sparky983.state/state"
      }
    ],
    sidebar: [
      {
        items: [
          { text: 'Installation', link: '/' },
          { text: 'Introduction', link: '/introduction'}
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/Sparky983/state' }
    ]
  }
})
