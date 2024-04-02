import potatoSmall from "../assets/potato-small.jpg";

export default function About() {
  return (
    <div className="flex flex-col justify-center items-center px-6 py-4">
      <div className="flex flex-col space-y-4 lg:w-1/2 bg-slate-50 rounded-md px-3 border-2 border-slate-100 py-2 lg:text-lg text-slate-800">
        <h1 className="font-bold text-2xl">About me</h1>
        <p className="text-justify">
          My real name is Rick and I am someone who likes to dabble in the fields of webdev, DIY electronics, 3D printing. Although my formal
          education is in molecular biology and genetics, I aspire to eventually work in the IT sector. Since my graduation from university in 2023 I
          have been self-learning Javascript/Typescript (React, Node), SQL (PostgreSQL), Java (Spring). Besides studying at a vocational school, I am
          also currently preparing for the Cisco CCNA exam, reading a book about operating systems (the one by Arpaci-Dusseau et al.) and working on
          this site.
        </p>
        <h1 className="font-bold text-2xl">About this site</h1>
        <p className="text-justify">
          This is my second site that I have ever built. It is a simple frontend combined with an even simpler backend. Besides being a project for
          learning web development, this personal website also serves as a place to share my thoughts, photos, projects. The site is currently being
          hosted on a potato PC laptop with no battery from 2015.
        </p>
        <section>
          <img alt="server" id="server-image" className="w-full   rounded-md mb-2" srcSet={potatoSmall} />
          <label htmlFor="server-image">
            <b>My webserver</b>. Current living place of this website.
          </label>
        </section>
        <p></p>
      </div>
    </div>
  );
}
