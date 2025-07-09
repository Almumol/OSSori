import React, { useEffect, useState } from 'react';
import ReactMarkdown from 'react-markdown';

interface MarkdownViewerProps {
  url: string;
}

const MarkdownViewer: React.FC<MarkdownViewerProps> = ({ url }) => {
  const [content, setContent] = useState<string>('');
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!url) return;
    setContent('');
    setError(null);
    fetch(url)
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch markdown');
        return res.text();
      })
      .then(setContent)
      .catch(err => setError(err.message));
  }, [url]);

  if (!url) return <div>기여 가이드가 없습니다.</div>;
  if (error) return <div>에러: {error}</div>;
  if (!content) return <div>로딩 중...</div>;

  return <ReactMarkdown>{content}</ReactMarkdown>;
};

export default MarkdownViewer;